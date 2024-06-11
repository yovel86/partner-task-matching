package com.partner_task.partner_location_service.controllers;

import com.partner_task.partner_location_service.dtos.UpdatePartnerLocationRequestDTO;
import com.partner_task.partner_location_service.exceptions.InvalidLatitudeException;
import com.partner_task.partner_location_service.exceptions.InvalidLongitudeException;
import com.partner_task.partner_location_service.services.PartnerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner-location")
public class PartnerLocationController {

    private final PartnerLocationService partnerLocationService;

    @Autowired
    public PartnerLocationController(PartnerLocationService partnerLocationService) {
        this.partnerLocationService = partnerLocationService;
    }

    @PostMapping("/{id}")
    public void updatePartnerLocation(
            @RequestBody UpdatePartnerLocationRequestDTO requestDTO,
            @RequestParam("id") long partnerId
    ) throws InvalidLatitudeException, InvalidLongitudeException {
        Double latitude = requestDTO.getLatitude();
        Double longitude = requestDTO.getLongitude();
        if(!isValidLatitude(latitude)) throw new InvalidLatitudeException("Invalid Latitude");
        if(!isValidLongitude(longitude)) throw new InvalidLongitudeException("Invalid Longitude");
        this.partnerLocationService.updatePartnerLocation(partnerId, latitude, longitude);
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
