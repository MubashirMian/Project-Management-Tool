package com.mian.ProjectMangementTool.model;

import com.mian.ProjectMangementTool.subscription.PlanType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate subscriptionStartDate;
    private LocalDate getSubscriptionEndDate;
    private PlanType planType;
    private boolean isValid;
    @OneToMany
    private User user;

    public Subscription() {
    }

    public Subscription(Long id, LocalDate subscriptionStartDate, LocalDate getSubscriptionEndDate, PlanType plantType, boolean isValid, User user) {
        this.id = id;
        this.subscriptionStartDate = subscriptionStartDate;
        this.getSubscriptionEndDate = getSubscriptionEndDate;
        this.planType = plantType;
        this.isValid = isValid;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(LocalDate subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public LocalDate getGetSubscriptionEndDate() {
        return getSubscriptionEndDate;
    }

    public void setGetSubscriptionEndDate(LocalDate getSubscriptionEndDate) {
        this.getSubscriptionEndDate = getSubscriptionEndDate;
    }

    public PlanType getPlantType() {
        return planType;
    }

    public void setPlantType(PlanType plantType) {
        this.planType = plantType;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
