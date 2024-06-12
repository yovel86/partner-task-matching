package com.partner_task.allocation_service.strategies;

import com.partner_task.allocation_service.dtos.Location;
import org.springframework.stereotype.Component;

@Component
public class HaversineStrategy implements CalculateDistanceStrategy {

    @Override
    public double calculateDistance(Location location1, Location location2) {
        final double EARTH_RADIUS = 6371; // in KM
        double lat1 = location1.getLatitude();
        double lat2 = location2.getLatitude();
        double long1 = location1.getLongitude();
        double long2 = location2.getLongitude();

        double diffLatitude = Math.toRadians(lat2 - lat1);
        double diffLongitude = Math.toRadians(long2 - long1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(diffLatitude / 2), 2) +
                   Math.pow(Math.sin(diffLongitude / 2), 2) *
                   Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }

}
