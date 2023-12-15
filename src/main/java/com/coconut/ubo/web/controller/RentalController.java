package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.image.ImageDetail;
import com.coconut.ubo.domain.image.ImageSet;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.item.RentalItem;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.image.ImageSetRepository;
import com.coconut.ubo.repository.item.ItemRepository;
import com.coconut.ubo.repository.user.LikeRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.item.ItemServiceImpl;
import com.coconut.ubo.service.user.UserServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.item.RentalItemRequest;
import com.coconut.ubo.web.dto.item.RentalItemResponse;
import com.coconut.ubo.web.mapper.RentalItemMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rental")
public class RentalController {

    private final ImageSetRepository imageSetRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemServiceImpl itemService;
    private final RentalItemMapper rentalItemMapper;
    private final UserServiceImpl userService;
    private final LikeRepository likeRepository;



    /**
     * 물품 등록
     */
    @PostMapping("/new")
    public ResponseEntity<?> createItem(@Login User loginUser,
                                        @ModelAttribute @Valid RentalItemRequest request) throws IOException {

        RentalItemResponse rentalItemResponse = itemService.saveRentalItem(loginUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalItemResponse);
    }


    /**
     * 물품 수정
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateItem(@Login User loginUser,
                                        @PathVariable("itemId") Long itemId,
                                        @ModelAttribute @Valid RentalItemRequest request) throws IOException {

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        // 로그인한 유저와 해당 item의 판매자가 같으면
        if (loginUser == item.getSeller()) {
            RentalItemResponse rentalItemResponse = itemService.updateRentalItem(itemId, request);
            return ResponseEntity.status(HttpStatus.OK).body(rentalItemResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("판매자만 수정이 가능합니다.");
        }
    }

    /**
     * 물품 목록
     */
    @GetMapping
    public ResponseEntity<?> getRentalItemList(@Login User loginUser,
                                               HttpServletRequest request,
                                               @RequestParam(defaultValue = "new") String sort,
                                               @RequestParam(required = false) String major,
                                               @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        List<Item> items = itemService.getFilteredItems(null,  "rental", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
    }

    /**
     * 물품 상세 페이지
     */
    @GetMapping("/{itemId}")
    @Transactional
    public ResponseEntity<?> getUsedItem(@Login User loginUser,
                                                          @PathVariable("itemId") Long itemId) throws IOException {

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        //조회수 증가
        item.incrementViewCount();

        if (!(item instanceof RentalItem rentalItem)) { //Item 인스턴스가 RentalItem 확인
            throw new IllegalArgumentException("Item is not a RentalItem");
        }

        //해당 엔티티의 ImageSet에 연관된 ImageDetail 목록에서 ImageURL 추출
        ImageSet imageSet = imageSetRepository.findByItem(rentalItem).orElseThrow(EntityNotFoundException::new);
        List<String> imageUrls = imageSet.getImageDetails().stream().map(ImageDetail::getFileUrl).collect(Collectors.toList());

        // 로그인한 사용자가 물품 좋아요 했는지 확인
        boolean isLiked = likeRepository.findByItemIdAndUserId(itemId, loginUser.getId()) != null;

        RentalItemResponse rentalItemResponse = rentalItemMapper.toDto(rentalItem, imageUrls, isLiked);
        return ResponseEntity.ok(rentalItemResponse);
    }




//    /**
//     * 물품 등록
//     */
//    @PostMapping("/new")
//    public ResponseEntity<?> createItem(
//                                        @ModelAttribute @Valid RentalItemRequest request) throws IOException {
//        // 세션 대신 하드코딩
//        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
//
//        RentalItemResponse rentalItemResponse = itemService.saveRentalItem(user, request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(rentalItemResponse);
//    }
//
//
//    /**
//     * 물품 수정
//     */
//    @PutMapping("/{itemId}")
//    public ResponseEntity<?> updateItem(
//                                        @PathVariable("itemId") Long itemId,
//                                        @ModelAttribute @Valid RentalItemRequest request) throws IOException {
//
//        // 세션 대신 하드코딩
//        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
//
//        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
//        // 로그인한 유저와 해당 item의 판매자가 같으면
//        if (user == item.getSeller()) {
//            RentalItemResponse rentalItemResponse = itemService.updateRentalItem(itemId, request);
//            return ResponseEntity.status(HttpStatus.OK).body(rentalItemResponse);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("판매자만 수정이 가능합니다.");
//        }
//    }
//
//    /**
//     * 물품 목록
//     */
//    @GetMapping
//    public ResponseEntity<?> getRentalItemList(
//                                               HttpServletRequest request,
//                                               @RequestParam(defaultValue = "new") String sort,
//                                               @RequestParam(required = false) String major,
//                                               @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {
//
//        List<Item> items = itemService.getFilteredItems(null,  "rental", sort, major, tradeAvailOnly);
//        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
//    }
//
//    /**
//     * 물품 상세 페이지
//     */
//    @GetMapping("/{itemId}")
//    @Transactional
//    public ResponseEntity<?> getUsedItem(
//                                         @PathVariable("itemId") Long itemId) throws IOException {
//
//        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
//        // 세션 대신 하드코딩
//        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
//
//        //조회수 증가
//        item.incrementViewCount();
//
//        if (!(item instanceof RentalItem rentalItem)) { //Item 인스턴스가 RentalItem 확인
//            throw new IllegalArgumentException("Item is not a RentalItem");
//        }
//
//        //해당 엔티티의 ImageSet에 연관된 ImageDetail 목록에서 ImageURL 추출
//        ImageSet imageSet = imageSetRepository.findByItem(rentalItem).orElseThrow(EntityNotFoundException::new);
//        List<String> imageUrls = imageSet.getImageDetails().stream().map(ImageDetail::getFileUrl).collect(Collectors.toList());
//
//        // 로그인한 사용자가 물품 좋아요 했는지 확인
//        boolean isLiked = likeRepository.findByItemIdAndUserId(itemId, user.getId()) != null;
//
//        RentalItemResponse rentalItemResponse = rentalItemMapper.toDto(rentalItem, imageUrls, isLiked);
//        return ResponseEntity.ok(rentalItemResponse);
//    }


}
