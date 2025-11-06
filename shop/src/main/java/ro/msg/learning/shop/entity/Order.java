package ro.msg.learning.shop.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name="orders")
public class Order{
    @Id
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserAccount user;

    @Column(name="created_at", nullable = false)
//    @Builder.Default
//    private LocalDateTime createdAt=LocalDateTime.now();
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
