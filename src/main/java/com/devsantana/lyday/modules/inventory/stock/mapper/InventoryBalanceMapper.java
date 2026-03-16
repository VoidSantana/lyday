package com.devsantana.lyday.modules.inventory.stock.mapper;

import com.devsantana.lyday.modules.company.location.model.Location;
import com.devsantana.lyday.modules.inventory.stock.dto.InventoryBalanceCreateDto;
import com.devsantana.lyday.modules.inventory.stock.dto.InventoryBalanceResponseDto;
import com.devsantana.lyday.modules.inventory.stock.model.InventoryBalance;
import com.devsantana.lyday.modules.products.model.Product;

public class InventoryBalanceMapper {

    private  InventoryBalanceMapper(){}

    public static InventoryBalance toEntity(
            InventoryBalanceCreateDto dto,
            Product product,
            Location location
    ){
        return InventoryBalance.builder()
                .product(product)
                .location(location)
                .quantity(dto.getQuantity())
                .build();
    }
    public static InventoryBalanceResponseDto toDto(InventoryBalance entity){
        return InventoryBalanceResponseDto.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .locationId(entity.getLocation().getId())
                .quantity(entity.getQuantity())
                .build();
    }
}
