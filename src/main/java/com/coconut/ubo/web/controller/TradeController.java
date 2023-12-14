package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.service.item.ItemServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.item.ItemListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class TradeController {

    private final ItemServiceImpl itemService;

    /**
     * 중고거래 - 판매 거래내역
     */
    @GetMapping("/sell")
    public ResponseEntity<List<ItemListResponse>> sellHistory(@Login User loginUser,
                                                              @RequestParam(defaultValue = "new") String sort,
                                                              @RequestParam(required = false) String major,
                                                              @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {
        List<Item> items = itemService.getMyFilteredItems(loginUser.getId(), "used", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
    }


    /**
     * 대여 거래내역
     */
    @GetMapping("/rental")
    public ResponseEntity<List<ItemListResponse>> rentalHistory(@Login User loginUser,
                                                              @RequestParam(defaultValue = "new") String sort,
                                                                @RequestParam(required = false) String major,
                                                              @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {
        List<Item> items = itemService.getMyFilteredItems(loginUser.getId(), "rental", sort, major, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
    }

}
