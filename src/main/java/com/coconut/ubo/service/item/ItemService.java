package com.coconut.ubo.service.item;

import com.coconut.ubo.domain.item.Item;
import com.coconut.ubo.web.dto.item.RentalItemRequest;
import com.coconut.ubo.web.dto.item.RentalItemResponse;
import com.coconut.ubo.web.dto.item.UsedItemRequest;
import com.coconut.ubo.web.dto.item.UsedItemResponse;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    UsedItemResponse saveUsedItem(UsedItemRequest request) throws IOException;
    RentalItemResponse saveRentalItem(RentalItemRequest request) throws IOException;
    UsedItemResponse updateUsedItem(Long id, UsedItemRequest request) throws IOException;
    RentalItemResponse updateRentalItem(Long id, RentalItemRequest request) throws IOException;

}
