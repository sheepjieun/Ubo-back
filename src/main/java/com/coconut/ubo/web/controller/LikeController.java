package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.Like;
import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.item.RentalItem;
import com.coconut.ubo.domain.item.UsedItem;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.user.LikeRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.like.LikeService;
import com.coconut.ubo.service.item.ItemServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.item.ItemListResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeRepository likeRepository;

    private final UserRepository userRepository;
    private final ItemServiceImpl itemService;
    private final LikeService likeService;

    /**
     * 좋아요 클릭 시 기능 처리
     */
    @PostMapping("/like/{itemId}")
    public ResponseEntity<?> likeItem(@PathVariable Long itemId) {

        //세션 대신 하드 코딩
        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        itemService.incrementLikeCount(itemId); // 좋아요 수 증가
        likeService.addLike(itemId, user.getId()); // like db 추가

        return ResponseEntity.ok().body("관심 목록에 추가되었습니다.");
    }

    /**
     * 마이페이지 관심내역 - 중고거래 조회
     */
    @GetMapping("/user/likes/used")
    public ResponseEntity<List<ItemListResponse>> getUserLikesUsed(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        //세션 대신 하드 코딩
        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        List<Item> items = likeRepository.findUsedItemsByUserId(user.getId());
        List<Item> filteredLikes = likeService.getFilteredLikes(items, sort, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(filteredLikes, null));
    }

    /**
     * 마이페이지 관심내역 - 대여 조회
     */
    @GetMapping("/user/likes/rental")
    public ResponseEntity<List<ItemListResponse>> getUserLikesRental(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly){

        //세션 대신 하드 코딩
        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        List<Item> items = likeRepository.findRentalItemsByUserId(user.getId());
        List<Item> filteredLikes = likeService.getFilteredLikes(items, sort, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(filteredLikes, null));
    }

}
