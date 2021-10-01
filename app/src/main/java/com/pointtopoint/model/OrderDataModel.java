package com.pointtopoint.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDataModel {


    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("status_id")
    private int status_id;
    @Expose
    @SerializedName("distance")
    private String distance;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("receivetime")
    private String receivetime;
    @Expose
    @SerializedName("time")
    private String time;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("to")
    private String to;
    @Expose
    @SerializedName("from")
    private String from;
    @Expose
    @SerializedName("id")
    private int id;

    public OrderDataModel(String status, int status_id, String distance, String phone, String receivetime, String time, String price, String to, String from, int id) {
        this.status = status;
        this.status_id = status_id;
        this.distance = distance;
        this.phone = phone;
        this.receivetime = receivetime;
        this.time = time;
        this.price = price;
        this.to = to;
        this.from = from;
        this.id = id;
    }

    public OrderDataModel() {
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
