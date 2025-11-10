package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderResponseDTO {
    private UUID orderId;
    private LocalDateTime orderDate;
    private DeliveryAddressDTO deliveryAddress;
    private UUID locationId;
    private List<ProductQuantityDTO> products;
}
