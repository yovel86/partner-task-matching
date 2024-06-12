package com.partner_task.partner_location_service.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class PartnerLocation implements Serializable {
    private int partnerId;
    private double latitude;
    private double longitude;
}
