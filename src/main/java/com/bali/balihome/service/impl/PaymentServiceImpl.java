package com.bali.balihome.service.impl;

import com.bali.balihome.dto.requestdto.PaymentRequestDto;
import com.bali.balihome.dto.responsedto.PaymentResponseDto;
import com.bali.balihome.exception.ResourceNotFoundException;
import com.bali.balihome.mapper.PaymentMapper;
import com.bali.balihome.model.domain.Order;
import com.bali.balihome.model.domain.Payment;
import com.bali.balihome.repository.OrderRepository;
import com.bali.balihome.repository.PaymentRepository;
import com.bali.balihome.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto dto) {
        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + dto.orderId()));

        Payment payment = paymentMapper.toEntity(dto);
        payment.setOrder(order);

        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDto updatePayment(Long id, PaymentRequestDto dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

        paymentMapper.updateEntityFromDto(dto, payment);

        if (!payment.getOrder().getId().equals(dto.orderId())) {
            Order newOrder = orderRepository.findById(dto.orderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + dto.orderId()));
            payment.setOrder(newOrder);
        }

        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
        return paymentMapper.toDto(payment);
    }

    @Override
    public List<PaymentResponseDto> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId).stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
        paymentRepository.delete(payment);
    }



}
