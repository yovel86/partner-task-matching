package com.partner_task.allocation_service.repositories;

import com.partner_task.allocation_service.models.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> { }
