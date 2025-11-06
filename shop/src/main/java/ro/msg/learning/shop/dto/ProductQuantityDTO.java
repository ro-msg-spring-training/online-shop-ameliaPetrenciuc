package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductQuantityDTO {
    private UUID productId;
    private Integer quantity;
}
