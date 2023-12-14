package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.image.ImageDetail;
import com.coconut.ubo.domain.image.ImageSet;
import com.coconut.ubo.domain.item.Category;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.item.UsedItem;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.image.ImageSetRepository;
import com.coconut.ubo.repository.item.ItemRepository;
import com.coconut.ubo.repository.user.LikeRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.item.ItemServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.item.UsedItemRequest;
import com.coconut.ubo.web.dto.item.UsedItemResponse;
import com.coconut.ubo.web.mapper.UsedItemMapper;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/used")
public class UsedController {
    private final LikeRepository likeRepository;

    private final ImageSetRepository imageSetRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemServiceImpl itemService;
    private final UsedItemMapper usedItemMapper;

    /**
     * 물품 등록
     */
    @PostMapping("/new")
    public ResponseEntity<?> createItem(@Login User loginUser,
                                                       @ModelAttribute @Valid UsedItemRequest request) throws IOException {

        UsedItemResponse usedItemResponse = itemService.saveUsedItem(loginUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usedItemResponse);
    }


    /**
     * 물품 수정
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateItem(@Login User loginUser,
                                                       @PathVariable("itemId") Long itemId,
                                                       @ModelAttribute @Valid UsedItemRequest request) throws IOException {

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        if (loginUser == item.getSeller()) {
            UsedItemResponse usedItemResponse = itemService.updateUsedItem(itemId, request);
            return ResponseEntity.status(HttpStatus.OK).body(usedItemResponse);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("판매자만 수정이 가능합니다.");
        }
    }

    /**
     * 물품 목록
     */
    @GetMapping()
    public ResponseEntity<?> getTotalUsedList(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        List<Item> items = itemService.getFilteredItems(null,  "used", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
    }

    @GetMapping("/book")
    public ResponseEntity<?> getBookUsedList(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        List<Item> items = itemService.getFilteredItems(null,  "used", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, Category.BOOK));
    }

    @GetMapping("/tool")
    public ResponseEntity<?> getToolUsedList(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        List<Item> items = itemService.getFilteredItems(null,  "used", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, Category.TOOL));
    }

    @GetMapping("/etc")
    public ResponseEntity<?> getEtcUsedList(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        List<Item> items = itemService.getFilteredItems(null,  "used", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, Category.ETC));
    }

    @GetMapping("/house")
    public ResponseEntity<?> getHouseUsedList(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        List<Item> items = itemService.getFilteredItems(null,  "used", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, Category.HOUSE));
    }

    @GetMapping("/buy")
    public ResponseEntity<?> getBuyUsedList(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        List<Item> items = itemService.getFilteredItems(null,  "used", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, Category.BUY));
    }

    /**
     * 물품 상세 페이지
     */
    @GetMapping("/{itemId}")
    @Transactional
    public ResponseEntity<?> getUsedItem(@Login User loginUser, @PathVariable("itemId") Long itemId) throws IOException {

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        //조회수 증가
        item.incrementViewCount();

        if (!(item instanceof UsedItem usedItem)) { //Item 인스턴스가 UsedItem인지 확인
            throw new IllegalArgumentException("Item is not a UsedItem");
        }

        //해당 엔티티의 ImageSet에 연관된 ImageDetail 목록에서 ImageURL 추출
        ImageSet imageSet = imageSetRepository.findByItem(usedItem).orElseThrow(EntityNotFoundException::new);
        List<String> imageUrls = imageSet.getImageDetails().stream().map(ImageDetail::getFileUrl).collect(Collectors.toList());

        // 로그인한 사용자가 물품 좋아요 했는지 확인
        boolean isLiked = likeRepository.findByItemIdAndUserId(itemId, loginUser.getId()) != null;

        UsedItemResponse usedItemResponse = usedItemMapper.toDto(usedItem, imageUrls, isLiked);
        return ResponseEntity.ok(usedItemResponse);
    }

}

//물품 등록
/*
        if (userService.isUserLoggedIn(loginUser)) {
            UsedItemResponse usedItemResponse = itemService.saveUsedItem(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(usedItemResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
*/

// 물품 수정
/*
        if (userService.isUserLoggedIn(loginUser)) {
            UsedItemResponse usedItemResponse = itemService.updateUsedItem(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(usedItemResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
*/

// 물품 목록
/*
        log.info("중고거래 페이지 - 로그인한 유저 정보 : {}", loginUser.getLoginId());

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("중고거래 Session ID: {}", session.getId());
        } else {
            log.info("중고거래 No active session found");
        }

        if (userService.isUserLoggedIn(loginUser)) {
            List<Item> items = itemService.getFilteredItems(null,  "used", sort, tradeAvailOnly);
            return ResponseEntity.ok(itemService.getItemListResponses(items, null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
*/

/*
        if (userService.isUserLoggedIn(loginUser)) {
            List<Item> items = itemService.getFilteredItems(null,"used", sort, tradeAvailOnly);
            return ResponseEntity.ok(itemService.getItemListResponses(items, Category.BOOK));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
*/

// 물품 상세 페이지
/*
        if (userService.isUserLoggedIn(loginUser)) {
            Item item = itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);

            if (!(item instanceof UsedItem usedItem)) { //Item 인스턴스가 UsedItem인지 확인
                throw new IllegalArgumentException("Item is not a UsedItem");
            }

            //해당 엔티티의 ImageSet에 연관된 ImageDetail 목록에서 ImageURL 추출
            ImageSet imageSet = imageSetRepository.findByItem(usedItem).orElseThrow(EntityNotFoundException::new);
            List<String> imageUrls = imageSet.getImageDetails().stream().map(ImageDetail::getFileUrl).collect(Collectors.toList());
            UsedItemResponse usedItemResponse = usedItemMapper.toDto(usedItem, imageUrls);

            return ResponseEntity.ok(usedItemResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
*/

