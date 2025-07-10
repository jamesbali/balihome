package com.bali.balihome.repository;

import com.bali.balihome.model.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository <Customer, Long> {


    // To check if a customer with this email already exists
    boolean existsByEmail(String email);

    // To retrieve a customer by email
    Optional<Customer> findByEmail(String email);
}
