package com.mian.ProjectMangementTool.service;

import com.mian.ProjectMangementTool.model.Subscription;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.subscription.PlanType;

public interface SubscriptionService {
    Subscription createSubscription(User user);
    Subscription getUsersSubscription(Long userId) throws Exception;
    Subscription upgradeSubscription(Long userId, PlanType planType);
    boolean isValid(Subscription subscription);
}
