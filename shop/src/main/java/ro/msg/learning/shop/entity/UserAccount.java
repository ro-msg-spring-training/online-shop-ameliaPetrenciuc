package ro.msg.learning.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_account")

public class UserAccount {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="username",unique=true, nullable=false)
    private String username;

    @Column(name="password", nullable=false)
    @Size(min=6, max=16)
    private String password;

    @Column(name="email_address", nullable=false)
    private String email;

    @Column(name="user_role", nullable=false)
    private String userRole;

}
