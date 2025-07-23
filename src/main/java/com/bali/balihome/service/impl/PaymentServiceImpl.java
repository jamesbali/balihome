package com.bali.balihome.service.impl;

import com.bali.balihome.dto.external.PaymentIntent;
import com.bali.balihome.dto.requestdto.PaymentConfirmationRequest;
import com.bali.balihome.dto.requestdto.PaymentInitiationRequest;
import com.bali.balihome.dto.requestdto.PaymentRequestDto;
import com.bali.balihome.dto.responsedto.PaymentInitiationResponse;
import com.bali.balihome.dto.responsedto.PaymentResponseDto;
import com.bali.balihome.exception.ResourceNotFoundException;
import com.bali.balihome.mapper.PaymentMapper;
import com.bali.balihome.model.domain.Customer;
import com.bali.balihome.model.domain.Order;
import com.bali.balihome.model.domain.Payment;
import com.bali.balihome.model.enums.PaymentMethod;
import com.bali.balihome.model.enums.PaymentSource;
import com.bali.balihome.model.enums.PaymentStatus;
import com.bali.balihome.repository.OrderRepository;
import com.bali.balihome.repository.PaymentRepository;
import com.bali.balihome.service.PaymentGatewayService;
import com.bali.balihome.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentGatewayService gatewayService;

    @Override
    public PaymentResponseDto createStaffPayment(PaymentRequestDto dto, String staffUsername) {
        log.info("Creating staff payment for order {} by {}", dto.orderId(), staffUsername);

        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + dto.orderId()));

        Payment payment = paymentMapper.toEntity(dto);
        payment.setOrder(order);
        payment.setProcessedByUsername(staffUsername);

        // Set default payment source if not provided
        if (payment.getPaymentSource() == null) {
            payment.setPaymentSource(PaymentSource.IN_STORE);
        }

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Staff payment created with ID: {}", savedPayment.getId());

        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentInitiationResponse initiateCustomerPayment(PaymentInitiationRequest request, String username) {
        log.info("Initiating customer payment for order {} by {}", request.orderId(), username);

        // Validate order exists and belongs to customer
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + request.orderId()));

        validateOrderOwnership(order, username);

        // Create payment intent with gateway
        String currency = request.currency() != null ? request.currency() : "USD";
        PaymentIntent intent = gatewayService.createPaymentIntent(request.amount(), currency);

        // Create pending payment record
        Payment payment = Payment.builder()
                .order(order)
                .amount(request.amount())
                .paymentMethod(PaymentMethod.CARD)  // Will be confirmed later
                .paymentStatus(PaymentStatus.PENDING)
                .paymentSource(PaymentSource.ONLINE)
                .gatewayPaymentIntentId(intent.id())
                .processedByUsername(username)
                .currency(currency)
                .paidAt(LocalDateTime.now())  // Will be updated on confirmation
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment intent created with ID: {}", savedPayment.getId());

        return new PaymentInitiationResponse(
                intent.clientSecret(),
                intent.id(),
                request.amount(),
                currency,
                request.orderId()
        );
    }

    @Override
    public PaymentResponseDto confirmCustomerPayment(PaymentConfirmationRequest request, String username) {
        log.info("Confirming customer payment for order {} by {}", request.orderId(), username);

        // Find pending payment
        Payment payment = paymentRepository.findByOrderIdAndGatewayPaymentIntentId(
                request.orderId(),
                request.paymentIntentId()
        ).orElseThrow(() -> new ResourceNotFoundException("Payment not found for order: " + request.orderId()));

        // Validate ownership
        validateOrderOwnership(payment.getOrder(), username);

        // Confirm payment with gateway
        PaymentIntent confirmedIntent = gatewayService.confirmPayment(request.paymentIntentId());

        // Update payment record
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setTransactionId(confirmedIntent.transactionId());
        payment.setPaidAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment confirmed with transaction ID: {}", confirmedIntent.transactionId());

        // TODO: Update order status to PAID
        // orderService.markOrderAsPaid(request.orderId());

        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto dto) {
        // Legacy method - defaults to in-store staff payment
        return createStaffPayment(dto, "SYSTEM");
    }

    @Override
    public PaymentResponseDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
        return paymentMapper.toDto(payment);
    }

    @Override
    public List<PaymentResponseDto> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getCustomerPayments(String username) {
        return paymentRepository.findByCustomerUsername(username)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public PaymentResponseDto updatePayment(Long id, PaymentRequestDto dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

        paymentMapper.updateEntityFromDto(dto, payment);

        // Update order if changed
        if (!payment.getOrder().getId().equals(dto.orderId())) {
            Order newOrder = orderRepository.findById(dto.orderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + dto.orderId()));
            payment.setOrder(newOrder);
        }

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
        paymentRepository.delete(payment);
        log.info("Payment deleted with ID: {}", id);
    }

    @Override
    public boolean isPaymentOwner(Long paymentId, String username) {
        return paymentRepository.findById(paymentId)
                .map(payment -> {
                    try {
                        Customer customer = payment.getOrder().getCustomer();

                        // If customer has a linked user account
                        if (customer.isRegisteredCustomer()) {
                            return customer.getUser().getUsername().equals(username);
                        }

                        // Guest customer - no ownership (staff only access)
                        log.debug("Payment {} belongs to guest customer, denying ownership for user {}",
                                paymentId, username);
                        return false;

                    } catch (Exception e) {
                        log.warn("Could not validate payment ownership for payment {} and user {}: {}",
                                paymentId, username, e.getMessage());
                        return false;
                    }
                })
                .orElse(false);
    }

    private void validateOrderOwnership(Order order, String username) {
        try {
            Customer customer = order.getCustomer();

            // Check if customer has a linked user account
            if (!customer.isRegisteredCustomer()) {
                throw new RuntimeException("Order belongs to guest customer, cannot be accessed by: " + username);
            }

            // Check if the linked user matches
            if (!customer.getUser().getUsername().equals(username)) {
                throw new RuntimeException("Order does not belong to customer: " + username);
            }

            log.debug("Order ownership validated for user {} and order {}", username, order.getId());

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error validating order ownership for order {} and user {}: {}",
                    order.getId(), username, e.getMessage());
            throw new RuntimeException("Could not validate order ownership", e);
        }
    }


}
