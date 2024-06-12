package com.partner_task.order_service.services;

import com.partner_task.order_service.models.Location;
import com.partner_task.order_service.models.Order;
import com.partner_task.order_service.repositories.OrderRepository;
import com.partner_task.order_service.repositories.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final RedisRepository redisRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(RedisRepository redisRepository, OrderRepository orderRepository) {
        this.redisRepository = redisRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order updatePickupLocation(long orderId, double latitude, double longitude) {
        Order order;
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        if(orderOptional.isEmpty()) {
            order = new Order();
            Location pickupLocation = new Location();
            pickupLocation.setLatitude(latitude);
            pickupLocation.setLongitude(longitude);
            order.setPickupLocation(pickupLocation);
            order = this.orderRepository.save(order);
            this.redisRepository.save("order_" + order.getId(), pickupLocation);
        } else {
            order = orderOptional.get();
            Location pickupLocation = order.getPickupLocation();
            pickupLocation.setLatitude(latitude);
            pickupLocation.setLongitude(longitude);
            order.setPickupLocation(pickupLocation);
            this.orderRepository.save(order);
            this.redisRepository.save("order_" + order.getId(), pickupLocation);
        }
        return order;
    }

}
