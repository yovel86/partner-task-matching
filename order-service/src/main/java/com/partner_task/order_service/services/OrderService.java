package com.partner_task.order_service.services;

public interface OrderService {
    void updatePickupLocation(long orderId, double latitude, double longitude);
}
