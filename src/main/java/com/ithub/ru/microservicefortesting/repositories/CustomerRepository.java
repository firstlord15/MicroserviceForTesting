package com.ithub.ru.microservicefortesting.repositories;

import com.ithub.ru.microservicefortesting.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
