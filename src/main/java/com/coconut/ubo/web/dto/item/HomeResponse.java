package com.coconut.ubo.web.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse {

    private List<ItemListResponse> popularItems;
    private List<ItemListResponse> usedItems;
    private List<ItemListResponse> rentalItems;

    public void addHomeItems(List<ItemListResponse> popularItems, List<ItemListResponse> usedItemsResponse, List<ItemListResponse> rentalItemsResponses) {
        this.popularItems = popularItems;
        this.usedItems = usedItemsResponse;
        this.rentalItems = rentalItemsResponses;
    }
}
