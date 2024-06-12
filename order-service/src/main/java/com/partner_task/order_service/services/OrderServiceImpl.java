package com.partner_task.order_service.services;

import com.partner_task.order_service.models.Location;
import com.partner_task.order_service.models.Order;
import com.partner_task.order_service.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RedisTemplate<String, Object> redisTemplate) {
        this.orderRepository = orderRepository;
        this.redisTemplate = redisTemplate;
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
            this.redisTemplate.opsForHash().put("ORDER_PICKUP_LOCATION", "order_" + order.getId(), pickupLocation);
        } else {
            order = orderOptional.get();
            Location pickupLocation = order.getPickupLocation();
            pickupLocation.setLatitude(latitude);
            pickupLocation.setLongitude(longitude);
            order.setPickupLocation(pickupLocation);
            this.orderRepository.save(order);
            this.redisTemplate.opsForHash().put("ORDER_PICKUP_LOCATION", "order_" + order.getId(), pickupLocation);
        }
        return order;
    }

}
