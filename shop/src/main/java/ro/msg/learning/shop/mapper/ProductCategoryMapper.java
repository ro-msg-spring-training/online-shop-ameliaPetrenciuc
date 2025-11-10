package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.ProductCategoryDTO;
import ro.msg.learning.shop.entity.ProductCategory;

public class ProductCategoryMapper {
    public static ProductCategoryDTO toDTO(ProductCategory productCategory) {
        return ProductCategoryDTO.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }

    public static ProductCategory toEntity(ProductCategoryDTO dto) {
        return ProductCategory.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
