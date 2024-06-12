package com.partner_task.allocation_service.strategies;

import com.partner_task.allocation_service.dtos.Location;

public interface CalculateDistanceStrategy {

    double calculateDistance(Location location1, Location location2);

}
