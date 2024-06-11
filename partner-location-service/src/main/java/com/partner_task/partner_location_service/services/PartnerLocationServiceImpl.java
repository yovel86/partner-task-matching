package com.partner_task.partner_location_service.services;

import com.partner_task.partner_location_service.models.PartnerLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PartnerLocationServiceImpl implements PartnerLocationService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public PartnerLocationServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void updatePartnerLocation(long partnerId, double latitude, double longitude) {
        PartnerLocation partnerLocation = (PartnerLocation) this.redisTemplate.opsForHash().get("PARTNER_LOCATION", "partner_" + partnerId);
        if(partnerLocation == null) {
            partnerLocation = new PartnerLocation();
        }
        partnerLocation.setLatitude(latitude);
        partnerLocation.setLongitude(longitude);
        this.redisTemplate.opsForHash().put("PARTNER_LOCATION", "partner_" + partnerId, partnerLocation);
    }

}
