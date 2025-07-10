package com.bali.balihome.service;

import com.bali.balihome.dto.requestdto.CustomerRequestDto;
import com.bali.balihome.dto.responsedto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto createCustomer(CustomerRequestDto dto);

    CustomerResponseDto updateCustomer(Long id, CustomerRequestDto dto);

    CustomerResponseDto getCustomerById(Long id);

    List<CustomerResponseDto> getAllCustomers();

    void deleteCustomer(Long id);
}
