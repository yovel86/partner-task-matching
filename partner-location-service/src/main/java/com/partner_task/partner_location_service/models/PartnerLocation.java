package com.partner_task.partner_location_service.models;

import lombok.Data;

@Data
public class PartnerLocation {
    private int partnerId;
    private double latitude;
    private double longitude;
}
