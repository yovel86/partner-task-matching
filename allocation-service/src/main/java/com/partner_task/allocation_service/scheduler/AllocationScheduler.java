package com.partner_task.allocation_service.scheduler;

import com.partner_task.allocation_service.strategies.CalculateDistanceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AllocationScheduler {

    private final CalculateDistanceStrategy calculateDistanceStrategy;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public AllocationScheduler(RedisTemplate<String, Object> redisTemplate, CalculateDistanceStrategy calculateDistanceStrategy) {
        this.redisTemplate = redisTemplate;
        this.calculateDistanceStrategy = calculateDistanceStrategy;
    }

    @Scheduled(cron = "0 */5 * * * *") // at every 5 minutes
    public void allocateTasks() {
        System.out.println("CRON task starting...");
        Map<Object, Object> orderLocations = redisTemplate.opsForHash().entries("ORDER_PICKUP_LOCATION");
        Map<Object, Object> partnerLocations = redisTemplate.opsForHash().entries("PARTNER_LOCATION");
        for(Map.Entry<Object, Object> location: orderLocations.entrySet()) {
            System.out.println(location);
        }
        for(Map.Entry<Object, Object> location: partnerLocations.entrySet()) {
            System.out.println(location);
        }
        System.out.println("CRON task finished...");
    }

}
