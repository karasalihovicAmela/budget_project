package com.javaacademy.entities;

import java.util.List;

/**
 * @author Amela Karasalihovic
 */
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Category> categories;
    private Double totalBalance;
    private Boolean loggedIn;

    public User() {
    }

    public User(Integer id, String firstName, String lastName, String email, String password,
                List<Category> categories, Double totalBalance, Boolean loggedIn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.categories = categories;
        this.totalBalance = totalBalance;
        this.loggedIn = loggedIn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
