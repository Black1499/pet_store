package com.lzx.entity;

import java.util.Date;

public class Order {
    private Integer id;

    private Integer petId;

    private Integer quantity;

    private Date shipDate;

    private String stratus;

    private Boolean complete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getStratus() {
        return stratus;
    }

    public void setStratus(String stratus) {
        this.stratus = stratus == null ? null : stratus.trim();
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}