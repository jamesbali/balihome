package com.bali.balihome.service.impl;

import com.bali.balihome.dto.requestdto.CustomerRequestDto;
import com.bali.balihome.dto.responsedto.CustomerResponseDto;
import com.bali.balihome.exception.DuplicateResourceException;
import com.bali.balihome.exception.ResourceNotFoundException;
import com.bali.balihome.mapper.CustomerMapper;
import com.bali.balihome.model.domain.Customer;
import com.bali.balihome.repository.CustomerRepository;
import com.bali.balihome.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto dto) {
        if (customerRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Customer already exists with email: " + dto.email());
        }

        Customer customer = customerMapper.toEntity(dto);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Override
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        // Only check for duplicate if email is changed
        if (!customer.getEmail().equals(dto.email()) && customerRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email already in use: " + dto.email());
        }

        customerMapper.updateEntityFromDto(dto, customer);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toDto(customer);
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }

}
