package com.javaacademy.entities;

import java.util.Date;

/**
 * @author Amela Karasalihovic
 */
public class Category {

    private Integer id;
    private Integer userId;
    private String name;
    private Double amount;
    private Double currentAmount;

    public Category() {
    }

    public Category(Integer id, Integer userId, String name, Double amount, Double currentAmount) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.currentAmount = currentAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
