package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.ProductRequestDTO;
import ro.msg.learning.shop.dto.ProductResponseDTO;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.ProductCategory;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto, ProductCategory category){
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .weight(dto.getWeight())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .build();
    }

    public static ProductResponseDTO toDTO(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .imageUrl(product.getImageUrl())
                .category(ProductCategoryMapper.toDTO(product.getCategory()))
                .build();
    }
}
