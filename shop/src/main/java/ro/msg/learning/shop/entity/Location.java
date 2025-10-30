package ro.msg.learning.shop.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="location")
public class Location {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="address_country")
    private String country;

    @Column(name="address_city")
    private String city;

    @Column(name="address_county")
    private String county;

    @Column(name="address_street_address")
    private String street;

    @OneToMany(mappedBy = "shippedFrom",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Stock> stocks;
}
