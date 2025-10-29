package ro.msg.learning.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product_category")

public class ProductCategory {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy ="category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList;

}
