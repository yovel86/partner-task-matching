package com.partner_task.allocation_service.services;

import com.partner_task.order_service.models.Order;
import com.partner_task.partner_location_service.models.PartnerLocation;

import java.util.List;
import java.util.Map;

public interface AllocationService {

    Map<Long, Long> allocateTasks(List<Order> orders, List<PartnerLocation> partnerLocations);

    void saveTasks(Map<Long, Long> partnerTaskMap);

}
