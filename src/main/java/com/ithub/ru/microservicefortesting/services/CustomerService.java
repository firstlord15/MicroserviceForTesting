package com.ithub.ru.microservicefortesting.services;

import com.ithub.ru.microservicefortesting.models.Customer;
import com.ithub.ru.microservicefortesting.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public boolean existsById(long id) {
        return customerRepository.existsById(id);
    }

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> updateById(long id, Customer newCustomer) {
        return customerRepository.findById(id).map(customer -> {
           customer.setAge(newCustomer.getAge());
           customer.setName(newCustomer.getName());
           customer.setSurname(newCustomer.getSurname());
            return customerRepository.save(customer);
        });
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
