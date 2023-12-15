package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.item.ItemServiceImpl;
import com.coconut.ubo.service.user.UserServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.item.ItemListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class HomeController {

    private final ItemServiceImpl itemService;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;


    /**
     * 메인 페이지 - 실시간 인기
     */
    @GetMapping("/home/popular")
    public ResponseEntity<?> loadHomePopular(@Login User loginUser) {

        if (userService.isUserLoggedIn(loginUser)) {
            List<ItemListResponse> popularItems = itemService.getPopularItems();
            return ResponseEntity.ok(popularItems);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }

    /**
     * 메인 페이지 - 최신 중고거래
     */
    @GetMapping("/home/used")
    public ResponseEntity<?> loadHomeUsed(@Login User loginUser) {

        if (userService.isUserLoggedIn(loginUser)) {
            List<Item> usedItems = itemService.getFilteredItems(null, "used", "new", null, true);
            List<ItemListResponse> usedItemsResponse = itemService.getItemListResponses(usedItems, null);
            return ResponseEntity.ok(usedItemsResponse);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }


    /**
     * 메인 페이지 - 최신 대여
     */
    @GetMapping("/home/rental")
    public ResponseEntity<?> loadHomeRental(@Login User loginUser) {

        if (userService.isUserLoggedIn(loginUser)) {
            List<Item> rentalItems = itemService.getFilteredItems(null, "rental", "new", null, true);
            List<ItemListResponse> rentalItemsResponses = itemService.getItemListResponses(rentalItems, null);
            return ResponseEntity.ok(rentalItemsResponses);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }

    /**
     * 물품 검색 - 중고거래
     */
    @GetMapping("/search/used")
    public ResponseEntity<?> searchUsedItems(
            @Login User loginUser,
            @RequestParam String q,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        if (userService.isUserLoggedIn(loginUser)) {
            List<Item> items = itemService.getFilteredItems(q, "used", sort, major, tradeAvailOnly);
            return ResponseEntity.ok(itemService.getItemListResponses(items, null));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

    }


    /**
     * 물품 검색 - 대여
     */
    @GetMapping("/search/rental")
    public ResponseEntity<?> searchRentalItems(
            @Login User loginUser,
            @RequestParam String q,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(required = false) String major,
            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        if (userService.isUserLoggedIn(loginUser)) {
            List<Item> items = itemService.getFilteredItems(q, "rental", sort, major, tradeAvailOnly);
            return ResponseEntity.ok(itemService.getItemListResponses(items, null));
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }








//    /**
//     * 로컬 전용
//     */
//
//    @GetMapping("/home/popular")
//    public ResponseEntity<?> loadHomePopular() {
//
//        List<ItemListResponse> popularItems = itemService.getPopularItems();
//        return ResponseEntity.ok(popularItems);
//    }
//
//    @GetMapping("/home/used")
//    public ResponseEntity<?> loadHomeUsed(@Login User loginUser) {
//
//        List<Item> usedItems = itemService.getFilteredItems(null, "used", "new", null, true);
//        List<ItemListResponse> usedItemsResponse = itemService.getItemListResponses(usedItems, null);
//        return ResponseEntity.ok(usedItemsResponse);
//    }
//
//    @GetMapping("/home/rental")
//    public ResponseEntity<?> loadHomeRental(@Login User loginUser) {
//
//        List<Item> rentalItems = itemService.getFilteredItems(null, "rental", "new",null,  true);
//        List<ItemListResponse> rentalItemsResponses = itemService.getItemListResponses(rentalItems, null);
//        return ResponseEntity.ok(rentalItemsResponses);
//    }
//
//    @GetMapping("/search/used")
//    public ResponseEntity<?> searchUsedItems(
//            @RequestParam String q,
//            @RequestParam(defaultValue = "new") String sort,
//            @RequestParam(required = false) String major,
//            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {
//
//        List<Item> items = itemService.getFilteredItems(q, "used", sort, major, tradeAvailOnly);
//        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
//    }
//
//    @GetMapping("/search/rental")
//    public ResponseEntity<?> searchRentalItems(
//            @RequestParam String q,
//            @RequestParam(defaultValue = "new") String sort,
//            @RequestParam(required = false) String major,
//            @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {
//
//        List<Item> items = itemService.getFilteredItems(q, "rental", sort, major, tradeAvailOnly);
//        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
//    }
//
//


}
