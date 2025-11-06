package ro.msg.learning.shop.service.strategy;

import lombok.*;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Product;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationProductQuantity {
    private Location location;
    private Product product;
    private int quantity;
}
