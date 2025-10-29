package ro.msg.learning.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="supplier")

public class Supplier {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name="name")
    private String name;

    @Column(name="phone_number")
    private String phoneNumber;
}
