package com.bali.balihome.service;

import com.bali.balihome.dto.requestdto.PaymentConfirmationRequest;
import com.bali.balihome.dto.requestdto.PaymentInitiationRequest;
import com.bali.balihome.dto.requestdto.PaymentRequestDto;
import com.bali.balihome.dto.responsedto.PaymentInitiationResponse;
import com.bali.balihome.dto.responsedto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {

    // Staff payment creation (in-store)
    PaymentResponseDto createStaffPayment(PaymentRequestDto dto, String staffUsername);

    // Customer e-commerce payment flow
    PaymentInitiationResponse initiateCustomerPayment(PaymentInitiationRequest request, String username);
    PaymentResponseDto confirmCustomerPayment(PaymentConfirmationRequest request, String username);

    // Legacy method (for backward compatibility)
    PaymentResponseDto createPayment(PaymentRequestDto dto);

    // Query methods
    PaymentResponseDto getPaymentById(Long id);
    List<PaymentResponseDto> getPaymentsByOrderId(Long orderId);
    List<PaymentResponseDto> getCustomerPayments(String username);
    List<PaymentResponseDto> getAllPayments();

    // Update and delete
    PaymentResponseDto updatePayment(Long id, PaymentRequestDto dto);
    void deletePayment(Long id);

    // Ownership validation
    boolean isPaymentOwner(Long paymentId, String username);

}
