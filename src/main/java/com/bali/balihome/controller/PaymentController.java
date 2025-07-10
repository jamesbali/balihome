package com.bali.balihome.controller;

import com.bali.balihome.dto.requestdto.PaymentRequestDto;
import com.bali.balihome.dto.responsedto.PaymentResponseDto;
import com.bali.balihome.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // Create a new payment
    @PostMapping
    public ResponseEntity<PaymentResponseDto> create(@Valid @RequestBody PaymentRequestDto dto) {
        PaymentResponseDto created = paymentService.createPayment(dto);
        return ResponseEntity.created(URI.create("/api/v1/payments/" + created.id())).body(created);
    }

    // Update an existing payment
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> update(@PathVariable Long id,
                                                     @Valid @RequestBody PaymentRequestDto dto) {
        return ResponseEntity.ok(paymentService.updatePayment(id, dto));
    }

    // Get a single payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    // Get all payments
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAll() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }


}
