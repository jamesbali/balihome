package com.bali.balihome.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.bali.balihome.security.user.User;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15)
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    // === ADD THIS RELATIONSHIP ===
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;  // Nullable for guest customers

    // === ADD THESE HELPER METHODS ===
    public boolean isRegisteredCustomer() {
        return user != null;
    }

    public boolean isGuestCustomer() {
        return user == null;
    }

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

