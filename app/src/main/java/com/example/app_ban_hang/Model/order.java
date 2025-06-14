package com.example.app_ban_hang.Model;

import java.util.Date;

public class order {
    private int orderId;
    private int userId;
    private Date orderDate;
    private double totalAmount;
    private String shippingAddress;
    private String status;

    public order(){}


    public order(String shippingAddress, double totalAmount, Date orderDate, int userId, String status) {
        this.shippingAddress = shippingAddress;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.userId = userId;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
