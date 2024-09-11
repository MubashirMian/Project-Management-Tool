package com.mian.ProjectMangementTool.service.imp;

import com.mian.ProjectMangementTool.model.Subscription;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.repository.SubscriptionRepository;
import com.mian.ProjectMangementTool.service.SubscriptionService;
import com.mian.ProjectMangementTool.service.UserService;
import com.mian.ProjectMangementTool.subscription.PlanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private UserService userService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();

        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlantType(PlanType.FREE);// free
        return null;
    }

    @Override
    public Subscription getUsersSubscription(Long userId) throws Exception {
        return null;
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlantType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());

        if(planType.equals(PlanType.ANNUALLY)){
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }
        else{
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        return false;
    }
}
