package com.partner_task.order_service.repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RedisRepository {

    private static final String KEY = "ORDER_PICKUP_LOCATION";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(String id, Object value) {
        hashOperations.put(KEY, id, value);
    }

    public Object findById(String id) {
        return hashOperations.get(KEY, id);
    }

    public Map<String, Object> findAll() {
        return hashOperations.entries(KEY);
    }

    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }

}
