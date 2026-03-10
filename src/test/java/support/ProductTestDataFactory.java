package support;

import com.devsantana.lyday.modules.products.dto.ProductCreateDto;
import com.devsantana.lyday.modules.products.dto.ProductResponseDto;
import com.devsantana.lyday.modules.products.dto.ProductUpdateDto;
import com.devsantana.lyday.modules.products.model.Product;

public class ProductTestDataFactory {

    public static ProductCreateDto createDto(){
        ProductCreateDto dto = new ProductCreateDto();
        dto.setName("Notebook Gamer");
        dto.setSku("SKU-123");
        dto.setStock(10);
        dto.setWeightKg(2.5);
        dto.setVolumeCm3(3000);
        dto.setDescription("NoteBook do bom");
        dto.setBrand("Dell");
        dto.setPrice(4500);
        return dto;
    }
    public static ProductUpdateDto updateDto(){
        ProductUpdateDto dto = new ProductUpdateDto();
        dto.setName("Notebook Gamer Atualizado");
        dto.setStock(5);
        dto.setWeightKg(2.3);
        dto.setVolumeCm3(2800);
        dto.setDescription("NoteBook do bom Atualizado");
        dto.setBrand("Dell");
        dto.setPrice(4300);
        return dto;
    }
    public static ProductResponseDto response(){
        return ProductResponseDto.builder()
                .id(1L)
                .name("Notebook Gamer")
                .sku("SKU-123")
                .stock(10)
                .weightKg(2.5)
                .volumeCm3(3000)
                .description("Notebook Potente")
                .brand("Dell")
                .price(4500)
                .build();
    }
    public static Product entity(){
        return Product.builder()
                .name("Notebook Gamer")
                .sku("SKU-123")
                .stock(10)
                .weightKg(2.5)
                .volumeCm3(3000)
                .description("Notebook Potente")
                .brand("Dell")
                .price(4500)
                .build();
    }
}