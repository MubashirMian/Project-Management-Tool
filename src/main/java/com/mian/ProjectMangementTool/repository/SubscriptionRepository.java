package com.mian.ProjectMangementTool.repository;

import com.mian.ProjectMangementTool.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Subscription findByUserId(Long userId);
}
