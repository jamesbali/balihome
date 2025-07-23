package com.bali.balihome.controller;

import com.bali.balihome.dto.requestdto.PaymentConfirmationRequest;
import com.bali.balihome.dto.requestdto.PaymentInitiationRequest;
import com.bali.balihome.dto.requestdto.PaymentRequestDto;
import com.bali.balihome.dto.responsedto.PaymentInitiationResponse;
import com.bali.balihome.dto.responsedto.PaymentResponseDto;
import com.bali.balihome.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // ===== E-COMMERCE CUSTOMER PAYMENTS =====

    @PostMapping("/initiate")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PaymentInitiationResponse> initiatePayment(
            @Valid @RequestBody PaymentInitiationRequest request,
            Authentication auth) {
        PaymentInitiationResponse response = paymentService.initiateCustomerPayment(request, auth.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PaymentResponseDto> confirmPayment(
            @Valid @RequestBody PaymentConfirmationRequest request,
            Authentication auth) {
        PaymentResponseDto created = paymentService.confirmCustomerPayment(request, auth.getName());
        return ResponseEntity.created(URI.create("/api/v1/payments/" + created.id())).body(created);
    }

    // ===== STAFF PAYMENTS =====

    @PostMapping("/staff")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<PaymentResponseDto> createStaffPayment(
            @Valid @RequestBody PaymentRequestDto dto,
            Authentication auth) {
        PaymentResponseDto created = paymentService.createStaffPayment(dto, auth.getName());
        return ResponseEntity.created(URI.create("/api/v1/payments/" + created.id())).body(created);
    }

    // ===== LEGACY ENDPOINT (for backward compatibility) =====

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<PaymentResponseDto> create(@Valid @RequestBody PaymentRequestDto dto, Authentication auth) {
        // Redirect to staff payment creation for backward compatibility
        return createStaffPayment(dto, auth);
    }

    // ===== QUERY ENDPOINTS =====

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN') or @paymentService.isPaymentOwner(#id, authentication.name)")
    public ResponseEntity<PaymentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/my-payments")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<PaymentResponseDto>> getMyPayments(Authentication auth) {
        return ResponseEntity.ok(paymentService.getCustomerPayments(auth.getName()));
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentsByOrderId(orderId));
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<PaymentResponseDto>> getAll() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // ===== UPDATE AND DELETE =====

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<PaymentResponseDto> update(@PathVariable Long id,
                                                     @Valid @RequestBody PaymentRequestDto dto) {
        return ResponseEntity.ok(paymentService.updatePayment(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }


}
