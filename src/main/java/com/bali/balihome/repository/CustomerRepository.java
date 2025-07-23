package com.bali.balihome.repository;

import com.bali.balihome.model.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository <Customer, Long> {


    // To check if a customer with this email already exists
    boolean existsByEmail(String email);

    // To retrieve a customer by email
    Optional<Customer> findByEmail(String email);

    // Find by email (for guest customers)

    // Find by linked user
    Optional<Customer> findByUserId(Long userId);
    Optional<Customer> findByUserUsername(String username);

    // Find registered customers only
    @Query("SELECT c FROM Customer c WHERE c.user IS NOT NULL")
    List<Customer> findRegisteredCustomers();

    // Find guest customers only
    @Query("SELECT c FROM Customer c WHERE c.user IS NULL")
    List<Customer> findGuestCustomers();

    // Check if user already has customer profile
    boolean existsByUserId(Long userId);
}
