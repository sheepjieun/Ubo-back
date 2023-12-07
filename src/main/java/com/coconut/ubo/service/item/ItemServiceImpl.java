package com.coconut.ubo.service.item;

import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.domain.image.ImageDetail;
import com.coconut.ubo.domain.image.ImageSet;
import com.coconut.ubo.domain.item.*;
import com.coconut.ubo.repository.image.ImageDetailRepository;
import com.coconut.ubo.repository.image.ImageSetRepository;
import com.coconut.ubo.repository.item.ItemRepository;
import com.coconut.ubo.repository.item.ItemRepositoryCustom;
import com.coconut.ubo.repository.user.LikeRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.S3Uploader;
import com.coconut.ubo.web.mapper.ItemListResponseMapper;
import com.coconut.ubo.web.mapper.RentalItemMapper;
import com.coconut.ubo.web.mapper.UsedItemMapper;
import com.coconut.ubo.web.dto.item.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final S3Uploader s3Uploader;
    private final ImageSetRepository imageSetRepository;
    private final ImageDetailRepository imageDetailRepository;
    private final RentalItemMapper rentalItemMapper;
    private final UsedItemMapper usedItemMapper;
    private final ItemListResponseMapper itemListResponseMapper;

    /**
     * 중고거래 물품 등록
     */
    @Transactional
    @Override
    public UsedItemResponse saveUsedItem(User user, UsedItemRequest request) throws IOException {

        UsedItem usedItem = usedItemMapper.toEntity(request, user); // DTO -> UsedItem 엔티티 변환 및 저장
        itemRepository.save(usedItem);

        ImageSet imageSet = new ImageSet(usedItem); // ImageSet 엔티티 생성 및 저장
        imageSetRepository.save(imageSet);

        List<String> imageUrls = uploadAndSaveItemImages(request, imageSet); // 이미지 S3 업로드 및 ImageDetail 엔티티 생성

        return usedItemMapper.toDto(usedItem, imageUrls);

    }

    /**
     * 대여 물품 등록
     */
    @Transactional
    @Override
    public RentalItemResponse saveRentalItem(User user, RentalItemRequest request) throws IOException {

        RentalItem rentalItem = rentalItemMapper.toEntity(request, user);
        itemRepository.save(rentalItem);

        ImageSet imageSet = new ImageSet(rentalItem);
        imageSetRepository.save(imageSet);

        List<String> imageUrls = uploadAndSaveItemImages(request, imageSet);

        log.info("대여물품 등록 시 startDate : {}", rentalItem.getStartDate());
        log.info("대여물품 등록 시 endDate : {}", rentalItem.getEndDate());

        return rentalItemMapper.toDto(rentalItem, imageUrls);

    }


    /**
     * 중고거래 물품 수정
     */
    @Transactional
    @Override
    public UsedItemResponse updateUsedItem(Long id, UsedItemRequest request) throws IOException {

        UsedItem usedItem = itemRepository.findById(id)
                .filter(item -> item instanceof UsedItem) // instanceof를 사용하여 Item 객체가 실제로 UsedItem의 인스턴스인지 확인
                .map(item -> (UsedItem) item) // Item 객체가 UsedItem의 인스턴스인 경우에만 map 연산을 수행하여 UsedItem으로 변환
                .orElseThrow(EntityNotFoundException::new); // Optional 객체가 null일 경우 예외 발생

//        usedItem.getSeller().getId();

        ImageSet imageSet = imageSetRepository.findByItem(usedItem).orElseThrow(EntityNotFoundException::new); // 기존 ImageSet 조회
        deleteImageDetail(imageSet); // 기존 ImageDetail 삭제
        List<String> imageUrls = uploadAndSaveItemImages(request, imageSet); // 새 ImageDetail 엔티티 생성하고 S3에 업로드
        usedItem.updateUsedItem(request); // Useditem 업데이트

        return usedItemMapper.toDto(usedItem, imageUrls);
    }

    /**
     * 대여 물품 수정
     */
    @Transactional
    @Override
    public RentalItemResponse updateRentalItem(Long itemId, RentalItemRequest request) throws IOException {

        RentalItem rentalItem = itemRepository.findById(itemId)
                .filter(item -> item instanceof RentalItem)
                .map(item -> (RentalItem) item)
                .orElseThrow(EntityNotFoundException::new);

        ImageSet imageSet = imageSetRepository.findByItem(rentalItem).orElseThrow(EntityNotFoundException::new);
        deleteImageDetail(imageSet);
        List<String> imageUrls = uploadAndSaveItemImages(request, imageSet);
        rentalItem.updateRentalItem(request);

//        return rentalItem.toRentalItemResponse(imageUrls);
        return rentalItemMapper.toDto(rentalItem, imageUrls);

    }

    /**
     * 물품 조회 - 물품 검색, 거래 타입, 물품 정렬, 거래 가능만
     */
    public List<Item> getFilteredItems(String search, String trade, String sort, boolean tradeAvailOnly) {

        if ("used".equalsIgnoreCase(trade)) {
            return itemRepository.findAllWithFilters(null, search, UsedItem.class, sort, tradeAvailOnly);
        } else if ("rental".equalsIgnoreCase(trade)) {
            return itemRepository.findAllWithFilters(null, search, RentalItem.class, sort, tradeAvailOnly);
        } else {
            throw new IllegalArgumentException("물품 거래 타입이 잘못 표시되었습니다.");
        }
    }

    /**
     *
     * 마이페이지 - 판매 목록 조회 - 유저 아이디, 거래 타입, 물품 정렬, 거래 가능만
     */
    public List<Item> getMyFilteredItems(Long userId, String trade, String sort, boolean tradeAvailOnly) {

        if ("used".equalsIgnoreCase(trade)) {
            return itemRepository.findAllWithFilters(userId, null, UsedItem.class, sort, tradeAvailOnly);
        } else if ("rental".equalsIgnoreCase(trade)) {
            return itemRepository.findAllWithFilters(userId, null, RentalItem.class, sort, tradeAvailOnly);
        } else {
            throw new IllegalArgumentException("물품 거래 타입이 잘못 표시되었습니다.");
        }
    }



    /**
     * 이미지들을 S3에 업로드 및 ImageDetial 엔티티 생성
     */
    public List<String> uploadAndSaveItemImages(ItemImageRequest request, ImageSet imageSet) throws IOException {

        List<String> imageUrls = new ArrayList<>();

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            AtomicInteger fileOrder = new AtomicInteger(1); // 파일 순서를 1부터 시작

            // 이미지들을 S3에 업로드 및 ImageDetial 엔티티 생성
            for (MultipartFile image : request.getImages()) {
                String imageUrl = s3Uploader.upload(image, "static"); // S3에 업로드
                String originalFilename = image.getOriginalFilename(); // 원본 파일명
                imageUrls.add(imageUrl);

                ImageDetail imageDetail = ImageDetail.builder()
                        .fileName(originalFilename)
                        .fileUrl(imageUrl)
                        .fileOrder(fileOrder.getAndIncrement()) //파일 순서 증가
                        .imageSet(imageSet)
                        .build();
                imageDetailRepository.save(imageDetail);
            }
        }

        return imageUrls;
    }

    /**
     * 기존 S3 이미지 및 ImageDetail 삭제
     */
    private void deleteImageDetail(ImageSet imageSet) {
        for (ImageDetail imageDetail : new ArrayList<>(imageSet.getImageDetails())) {
            s3Uploader.deleteFileFromS3Bucket(imageDetail.getFileUrl()); // S3에서 이미지 삭제
            imageSet.getImageDetails().remove(imageDetail); // ImageSet에서 ImageDetail 제거
            imageDetailRepository.delete(imageDetail); // 데이터베이스에서 ImageDetail 삭제
        }
    }


    // Item 리스트 -> ItemListResponse DTO 리스트로 변환
    public List<ItemListResponse> getItemListResponses(List<Item> items, Category category) {
        return items.stream()
                .map(item -> itemListResponseMapper.toDto(item, category))
                .filter(Objects::nonNull) // null인 응답은 제외
                .collect(Collectors.toList());
    }


    //실시간 인기상품 쿼리 결과 받아서 정렬
    public List<ItemListResponse> getPopularItems() {

        List<Item> popularItems = itemRepository.findPopularItems(PageRequest.of(0, 6));
        log.info("popularItems = {}", popularItems);
        return popularItems.stream()
                .map(item -> itemListResponseMapper.toDto(item, null))
                .collect(Collectors.toList());
    }

    // 상품의 like_count 증가 메서드
    public void incrementLikeCount(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        item.incrementLikeCount();
        itemRepository.save(item);
    }

}