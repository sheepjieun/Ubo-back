package com.coconut.ubo.service.item;

import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.domain.user.User;
import com.coconut.ubo.web.dto.item.RentalItemRequest;
import com.coconut.ubo.web.dto.item.RentalItemResponse;
import com.coconut.ubo.web.dto.item.UsedItemRequest;
import com.coconut.ubo.web.dto.item.UsedItemResponse;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    UsedItemResponse saveUsedItem(User user, UsedItemRequest request) throws IOException;
    RentalItemResponse saveRentalItem(User user, RentalItemRequest request) throws IOException;
    UsedItemResponse updateUsedItem(Long id, UsedItemRequest request) throws IOException;
    RentalItemResponse updateRentalItem(Long id, RentalItemRequest request) throws IOException;
    List<Item> getFilteredItems(String search, String trade, String sort, String major, boolean tradeAvailOnly);

    List<Item> getMyFilteredItems(Long id, String trade, String sort, String major, boolean tradeAvailOnly);

}
