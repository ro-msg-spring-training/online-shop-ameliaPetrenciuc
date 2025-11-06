package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductCategoryDTO {
    private UUID id;
    private String name;
    private String description;
}
