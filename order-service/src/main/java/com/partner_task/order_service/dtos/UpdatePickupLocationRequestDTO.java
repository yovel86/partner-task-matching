package com.partner_task.order_service.dtos;

import lombok.Data;

@Data
public class UpdatePickupLocationRequestDTO {
    private Double latitude;
    private Double longitude;
}
