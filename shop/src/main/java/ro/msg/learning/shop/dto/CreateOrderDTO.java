package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CreateOrderDTO {
    private LocalDateTime orderDate;
    private DeliveryAddressDTO deliveryAddress;
    private List<ProductQuantityDTO> products;
}
