package com.partner_task.order_service.controllers;

import com.partner_task.order_service.dtos.UpdatePickupLocationRequestDTO;
import com.partner_task.order_service.exceptions.InvalidLatitudeException;
import com.partner_task.order_service.exceptions.InvalidLongitudeException;
import com.partner_task.order_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{id}/pickup-location")
    public void updatePickupLocation(
            @RequestParam("id") long id,
            @RequestBody UpdatePickupLocationRequestDTO requestDTO
    ) throws InvalidLatitudeException, InvalidLongitudeException {
        Double latitude = requestDTO.getLatitude();
        Double longitude = requestDTO.getLongitude();
        if(!isValidLatitude(latitude)) throw new InvalidLatitudeException("Invalid Latitude");
        if(!isValidLongitude(longitude)) throw new InvalidLongitudeException("Invalid Longitude");
        this.orderService.updatePickupLocation(id, latitude, longitude);
    }

    private boolean isValidLatitude(Double latitude) {
        if(latitude == null) return false;
        return latitude >= -90 && latitude <= 90;
    }

    private boolean isValidLongitude(Double longitude) {
        if(longitude == null) return false;
        return longitude >= -180 && longitude <= 180;
    }

}
