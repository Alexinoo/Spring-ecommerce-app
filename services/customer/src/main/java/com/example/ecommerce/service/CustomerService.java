package com.example.ecommerce.service;

import com.example.ecommerce.customer.Customer;
import com.example.ecommerce.customer.CustomerRequest;
import com.example.ecommerce.customer.CustomerResponse;
import com.example.ecommerce.exception.CustomerNotFoundException;
import com.example.ecommerce.mapper.CustomerMapper;
import com.example.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest existingRequest) {
        var existingCustomer = customerRepository.findById(existingRequest.id())
                .orElseThrow(()-> new CustomerNotFoundException(
                        format("Cannot update customer:: No customer found with provided ID:: %s",existingRequest.id())
                ));
        mergerCustomer(existingCustomer , existingRequest);
        customerRepository.save(existingCustomer);
    }

    private void mergerCustomer(Customer existingCustomer, CustomerRequest existingRequest) {
        if (StringUtils.isNotBlank(existingRequest.firstname()))
            existingCustomer.setFirstname(existingRequest.firstname());

        if (StringUtils.isNotBlank(existingRequest.lastname()))
            existingCustomer.setLastname(existingRequest.lastname());

        if (StringUtils.isNotBlank(existingRequest.email()))
            existingCustomer.setEmail(existingRequest.email());

        if (existingRequest.address() != null)
            existingCustomer.setAddress(existingRequest.address());
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());

    }
    public Boolean existById(String customerId) {
        return customerRepository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(()-> new CustomerNotFoundException(
                        format("No customer found with the provided ID:: %s",customerId)
                ));

    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
