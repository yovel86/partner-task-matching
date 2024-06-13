package com.partner_task.allocation_service.scheduler;

import com.partner_task.allocation_service.repositories.RedisRepository;
import com.partner_task.allocation_service.strategies.CalculateDistanceStrategy;
import com.partner_task.order_service.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AllocationScheduler {

    private final RedisRepository redisRepository;
    private final CalculateDistanceStrategy calculateDistanceStrategy;

    @Autowired
    public AllocationScheduler(RedisRepository redisRepository, CalculateDistanceStrategy calculateDistanceStrategy) {
        this.redisRepository = redisRepository;
        this.calculateDistanceStrategy = calculateDistanceStrategy;
    }

    @Scheduled(cron = "0 */5 * * * *") // at every 5 minutes
    public void allocateTasks() {
        System.out.println("CRON task starting...");
        Map<String, Object> orderLocations = redisRepository.findAll("ORDERS");
        Map<String, Object> partnerLocations = redisRepository.findAll("PARTNER_LOCATION");
        for(Map.Entry<String, Object> location: orderLocations.entrySet()) {
            System.out.println(location);
        }
        for(Map.Entry<String, Object> location: partnerLocations.entrySet()) {
            System.out.println(location);
        }
        System.out.println("CRON task finished...");
    }

}
