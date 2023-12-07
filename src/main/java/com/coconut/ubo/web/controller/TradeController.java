package com.coconut.ubo.web.controller;

import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.repository.user.UserRepository;
import com.coconut.ubo.service.item.ItemServiceImpl;
import com.coconut.ubo.web.argumentresolver.Login;
import com.coconut.ubo.web.dto.item.ItemListResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class TradeController {

    private final ItemServiceImpl itemService;
    private final UserRepository userRepository;

    /**
     * 중고거래 - 판매 거래내역
     */
    @GetMapping("/sell")
    public ResponseEntity<List<ItemListResponse>> sellHistory(@Login User loginUser,
                                                              @RequestParam(defaultValue = "new") String sort,
                                                              @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        // 세션 대신 하드코딩
        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        List<Item> items = itemService.getMyFilteredItems(user.getId(), "used", sort, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
    }


    /**
     * 대여 거래내역
     */
    @GetMapping("/rental")
    public ResponseEntity<List<ItemListResponse>> rentalHistory(@Login User loginUser,
                                                              @RequestParam(defaultValue = "new") String sort,
                                                              @RequestParam(defaultValue = "false") boolean tradeAvailOnly) {

        // 세션 대신 하드코딩
        User user = userRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        List<Item> items = itemService.getMyFilteredItems(user.getId(), "rental", sort, tradeAvailOnly);
        return ResponseEntity.ok(itemService.getItemListResponses(items, null));
    }

}
