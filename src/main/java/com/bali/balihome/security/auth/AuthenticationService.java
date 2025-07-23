package com.bali.balihome.security.auth;

import com.bali.balihome.model.domain.Customer;
import com.bali.balihome.repository.CustomerRepository;
import com.bali.balihome.security.jwt.JwtService;
import com.bali.balihome.security.user.Role;
import com.bali.balihome.security.user.User;
import com.bali.balihome.security.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository; // ADD THIS
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request, Role role) {
        log.info("Registering new user with username: {} and role: {}", request.username(), role);

        // Validate uniqueness
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        // Create User account
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .role(role)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getId());

        // === NEW: Create Customer profile for CUSTOMER role ===
        if (role == Role.CUSTOMER) {
            createCustomerProfile(savedUser, request);
        }

        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);

        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                "Bearer",
                86400 // 24 hours in seconds
        );
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.identifier(),
                        request.password()
                )
        );

        var user = (User) authentication.getPrincipal();
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                "Bearer",
                86400 // 24 hours in seconds
        );
    }

    // === NEW PRIVATE METHOD ===
    private void createCustomerProfile(User user, RegisterRequest request) {
        try {
            Customer customer = Customer.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .phone(request.phoneNumber() != null ? request.phoneNumber() : "")
                    .address("") // Will be updated later by customer
                    .user(user) // Link to user account
                    .build();

            Customer savedCustomer = customerRepository.save(customer);
            log.info("Customer profile created with ID: {} for user: {}",
                    savedCustomer.getId(), user.getUsername());

        } catch (Exception e) {
            log.error("Failed to create customer profile for user: {}", user.getUsername(), e);
            // Don't throw exception - user account was already created successfully
            // Customer profile can be created later if needed
        }
    }

}
