package com.partner_task.allocation_service.scheduler;

import com.partner_task.allocation_service.repositories.RedisRepository;
import com.partner_task.allocation_service.services.AllocationService;
import com.partner_task.order_service.models.Order;
import com.partner_task.partner_location_service.models.PartnerLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AllocationScheduler {

    private final RedisRepository redisRepository;
    private final AllocationService allocationService;

    @Autowired
    public AllocationScheduler(RedisRepository redisRepository, AllocationService allocationService) {
        this.redisRepository = redisRepository;
        this.allocationService = allocationService;
    }

    @Scheduled(cron = "0 */5 * * * *") // runs at every 5 minutes
    public void allocateTasks() {
        System.out.println("CRON task starting...Trying to allocate tasks to nearest Partner");
        Map<String, Order> ordersMap =  castEntriesToOrder(redisRepository.findAll("ORDERS"));
        Map<String, PartnerLocation> partnerLocationsMap = castEntriesToPartnerLocations(redisRepository.findAll("PARTNER_LOCATION"));
        List<Order> orders = convertToList(ordersMap);
        List<PartnerLocation> partnerLocations = convertToList(partnerLocationsMap);
        Map<Long, Long> partnerTasks = this.allocationService.allocateTasks(orders, partnerLocations);
        this.allocationService.saveTasks(partnerTasks);
        System.out.println("CRON task ended, Tasks are assigned to nearest Partner");
    }

    private Map<String, Order> castEntriesToOrder(Map<String, Object> redisData) {
        Map<String, Order> orderData = new HashMap<>();
        for (Map.Entry<String, Object> entry : redisData.entrySet()) {
            orderData.put(entry.getKey(), (Order) entry.getValue());
        }
        return orderData;
    }

    private Map<String, PartnerLocation> castEntriesToPartnerLocations(Map<String, Object> redisData) {
        Map<String, PartnerLocation> partnerLocationData = new HashMap<>();
        for (Map.Entry<String, Object> entry : redisData.entrySet()) {
            partnerLocationData.put(entry.getKey(), (PartnerLocation) entry.getValue());
        }
        return partnerLocationData;
    }

    private <T> List<T> convertToList(Map<String, T> entries) {
        List<T> list = new ArrayList<>();
        for(Map.Entry<String, T> entry: entries.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

}
