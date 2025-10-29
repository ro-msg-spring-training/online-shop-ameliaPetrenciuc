package ro.msg.learning.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entity.keys.OrderDetailId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_detail")

public class OrderDetail {
    @EmbeddedId
    private OrderDetailId orderDetailId;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="shipped_from")
    private Location shippedFrom;

    @Column(name="quantity", nullable = false)
    @Min(1)
    private Integer quantity;


}
