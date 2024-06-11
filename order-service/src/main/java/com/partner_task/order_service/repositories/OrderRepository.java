package com.partner_task.order_service.repositories;

import com.partner_task.order_service.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> { }
