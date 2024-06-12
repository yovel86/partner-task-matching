package com.partner_task.order_service.services;

import com.partner_task.order_service.models.Order;

public interface OrderService {
    Order updatePickupLocation(long orderId, double latitude, double longitude);
}
