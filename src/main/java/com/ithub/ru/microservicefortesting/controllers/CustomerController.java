package com.ithub.ru.microservicefortesting.controllers;

import com.ithub.ru.microservicefortesting.exceptionHandler.ResourceNotFoundException;
import com.ithub.ru.microservicefortesting.models.Customer;
import com.ithub.ru.microservicefortesting.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // API requests
    @PostMapping("/api/customers")
    public ResponseEntity<Customer> newCustomer(@Valid @RequestBody Customer customer) {
        Customer saveCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCustomer);
    }

    @GetMapping("/api/customers")
    @ResponseBody
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/api/customer/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found by id: " + id));
    }

    @PutMapping("/api/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @Valid @RequestBody Customer newCustomer) {
        return customerService.updateById(id, newCustomer)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Customer not found by id {0} or cannot update", id)));
    }

    @DeleteMapping("/api/Customer/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") long id) {
        if (!customerService.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found by id: " + id);
        }

        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}