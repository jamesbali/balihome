package com.bali.balihome.service;

import com.bali.balihome.dto.requestdto.PaymentRequestDto;
import com.bali.balihome.dto.responsedto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto dto);

    PaymentResponseDto updatePayment(Long id, PaymentRequestDto dto);

    PaymentResponseDto getPaymentById(Long id);

    List<PaymentResponseDto> getPaymentsByOrderId(Long orderId);

    List<PaymentResponseDto> getAllPayments();

    void deletePayment(Long id);
}
