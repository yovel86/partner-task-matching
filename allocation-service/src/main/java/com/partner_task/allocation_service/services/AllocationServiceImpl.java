package com.partner_task.allocation_service.services;

import com.partner_task.allocation_service.models.Allocation;
import com.partner_task.allocation_service.repositories.AllocationRepository;
import com.partner_task.allocation_service.strategies.CalculateDistanceStrategy;
import com.partner_task.order_service.models.Location;
import com.partner_task.order_service.models.Order;
import com.partner_task.partner_location_service.models.PartnerLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AllocationServiceImpl implements AllocationService {

    private final AllocationRepository allocationRepository;
    private final CalculateDistanceStrategy calculateDistanceStrategy;

    @Autowired
    public AllocationServiceImpl(AllocationRepository allocationRepository, CalculateDistanceStrategy calculateDistanceStrategy) {
        this.allocationRepository = allocationRepository;
        this.calculateDistanceStrategy = calculateDistanceStrategy;
    }

    @Override
    public Map<Long, Long> allocateTasks(List<Order> orders, List<PartnerLocation> partnerLocations) {
        Map<Long, Long> partnerTaskMap = new HashMap<>();
        for(Order order: orders) {
            double shortestDistance = Double.MAX_VALUE;
            PartnerLocation nearestPartner = null;
            for(PartnerLocation partnerLocation: partnerLocations) {
                Location orderLocation = order.getPickupLocation();
                Location partnerCurrentLocation = new Location();
                partnerCurrentLocation.setLatitude(partnerLocation.getLatitude());
                partnerCurrentLocation.setLongitude(partnerLocation.getLongitude());
                double distance = this.calculateDistanceStrategy.calculateDistance(orderLocation, partnerCurrentLocation);
                if(distance < shortestDistance) {
                    nearestPartner = partnerLocation;
                    shortestDistance = distance;
                }
            }
            if(nearestPartner != null) {
                partnerTaskMap.put(order.getId(), nearestPartner.getPartnerId());
                partnerLocations.remove(nearestPartner);
            }
        }
        return partnerTaskMap;
    }

    @Override
    public void saveTasks(Map<Long, Long> partnerTaskMap) {
        for(Map.Entry<Long, Long> entry: partnerTaskMap.entrySet()) {
            Allocation allocation = new Allocation();
            allocation.setOrderId(entry.getKey());
            allocation.setPartnerId(entry.getValue());
            this.allocationRepository.save(allocation);
        }
    }

}
