package com.javaacademy.entities;

import java.util.Date;

/**
 * @author Amela Karasalihovic
 */
public class Category {

    private String categoryName;
    private Double amount;
    private Double currentAmount;
    private Date transactionDate;

    public Category() {
    }

    public Category(String categoryName, Double amount, Double currentAmount, Date transactionDate) {
        this.categoryName = categoryName;
        this.amount = amount;
        this.currentAmount = currentAmount;
        this.transactionDate = transactionDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
