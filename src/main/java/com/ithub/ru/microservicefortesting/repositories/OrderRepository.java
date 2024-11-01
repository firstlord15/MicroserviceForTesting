package com.ithub.ru.microservicefortesting.repositories;

import com.ithub.ru.microservicefortesting.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}