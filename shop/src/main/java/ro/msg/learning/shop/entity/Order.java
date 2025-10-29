package ro.msg.learning.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")

public class Order{
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private UserAccount user;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="address_country")
    private String country;

    @Column(name="address_city")
    private String city;

    @Column(name="address_county")
    private String county;

    @Column(name="address_street_address")
    private String street;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<OrderDetail> orderDetailList;

}
