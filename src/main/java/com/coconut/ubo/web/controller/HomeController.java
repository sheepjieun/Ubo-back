package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.service.item.ItemServiceImpl;
import com.coconut.ubo.service.user.UserServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.item.HomeResponse;
import com.coconut.ubo.web.dto.item.ItemListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class HomeController {

    private final ItemServiceImpl itemService;
    private final UserServiceImpl userService;

    /**
     * 메인 페이지
     */
    @GetMapping("/")
    public ResponseEntity<?> loadHome(@Login User loginUser) {

        log.info("메인 페이지에서 로그인한 유저 정보 : {}", loginUser.getLoginId());

        if (userService.isUserLoggedIn(loginUser)) {
            //TODO 인기물품 조회 로직 null 에러남
            List<Item> usedItems = itemService.getFilteredItems(null, "used", "new", true);
            List<Item> rentalItems = itemService.getFilteredItems(null, "rental", "new", true);
            List<ItemListResponse> popularItems = itemService.getPopularItems();
            List<ItemListResponse> usedItemsResponse = itemService.getItemListResponses(usedItems, null);
            List<ItemListResponse> rentalItemsResponses = itemService.getItemListResponses(rentalItems, null);

            HomeResponse response = new HomeResponse();
            response.addHomeItems(popularItems, usedItemsResponse, rentalItemsResponses);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }

    /**
     * 물품 검색
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchItems(
            @Login User loginUser,
            @RequestParam String q,
            @RequestParam(defaultValue = "used") String trade,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        if (userService.isUserLoggedIn(loginUser)) {
            List<Item> items = itemService.getFilteredItems(q, trade, sort, tradeAvailOnly);
            return ResponseEntity.ok(itemService.getItemListResponses(items, null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }
}
