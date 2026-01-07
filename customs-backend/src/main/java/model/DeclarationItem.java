package com.customs.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class DeclarationItem {

    @NotBlank(message = "Item description is required")
    private String desc;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int qty;

    @Min(value = 0, message = "Value cannot be negative")
    private double val;

    // Getters and Setters
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getVal() { return val; }
    public void setVal(double val) { this.val = val; }
}