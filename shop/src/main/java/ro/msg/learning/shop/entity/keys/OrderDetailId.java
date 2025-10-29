package ro.msg.learning.shop.entity.keys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailId implements Serializable {
    private UUID orderId;
    private UUID productId;
}
