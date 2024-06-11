package com.partner_task.order_service.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    private Location pickupLocation;
    @Embedded
    private Location dropLocation;
}
