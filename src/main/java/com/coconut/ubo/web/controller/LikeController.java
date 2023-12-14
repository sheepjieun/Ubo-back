package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.user.LikeRepository;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.item.ItemServiceImpl;
import com.coconut.ubo.service.like.LikeService;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.item.ItemListResponse;
import com.coconut.ubo.web.dto.like.LikeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<LikeResponse> likeItem(@Login User loginUser, @PathVariable Long itemId) {

        LikeResponse response;
        //LikeRepository에서 item, user 확인했는데 값 나옴
        if (likeRepository.findByItemIdAndUserId(itemId, loginUser.getId()) != null) {
            //좋아요 취소
            itemService.decrementLikeCount(itemId); // 좋아요 수 감소
            likeService.removeLike(itemId, loginUser.getId()); // like db 삭제
            int likeCount = itemService.getLikeCount(itemId); // 좋아요 수 조회
            response = new LikeResponse("관심 목록에서 삭제되었습니다.", likeCount);

        } else {
            // 좋아요 추가
            itemService.incrementLikeCount(itemId); // 좋아요 수 증가
            likeService.addLike(itemId, loginUser.getId()); // like db 추가
            int likeCount = itemService.getLikeCount(itemId); // 좋아요 수 조회
            response = new LikeResponse("관심 목록에 추가되었습니다.", likeCount);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * 마이페이지 관심내역 - 중고거래 조회
     */
    @GetMapping("/user/likes/used")
    public ResponseEntity<List<ItemListResponse>> getUserLikesUsed(
            @Login User loginUser,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        List<Item> items = likeRepository.findUsedItemsByUserId(loginUser.getId());
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

        List<Item> items = likeRepository.findRentalItemsByUserId(loginUser.getId());
        List<Item> filteredLikes = likeService.getFilteredLikes(items, sort, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(filteredLikes, null));
    }

}
