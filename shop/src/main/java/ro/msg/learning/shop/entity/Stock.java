package ro.msg.learning.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entity.keys.StockId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="stock")

public class Stock {
    @EmbeddedId
    private StockId stockId;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    @MapsId("locationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="location_id", nullable = false)
    private Location location;

    @Column(name="quantity")
    @Min(0)
    private Integer quantity;
}
