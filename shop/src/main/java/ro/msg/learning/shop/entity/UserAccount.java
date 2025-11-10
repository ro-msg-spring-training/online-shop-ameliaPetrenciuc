package ro.msg.learning.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ro.msg.learning.shop.util.UserRole;

import java.util.UUID;

@Entity
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    @Column(name="user_role", nullable=false)
    private UserRole userRole;
}
