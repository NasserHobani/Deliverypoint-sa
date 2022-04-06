package com.mndoobk_d.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driverdata {
    @Expose
    @SerializedName("is_sub_or_percen")
    private int is_sub_or_percen;
    @Expose
    @SerializedName("balance")
    private int balance;
    @Expose
    @SerializedName("active")
    private int active;
    @Expose
    @SerializedName("orders_count_canceled")
    private int orders_count_canceled;
    @Expose
    @SerializedName("orders_count")
    private int orders_count;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("subscribed_ex_date")
    private String subscribed_ex_date;
    @Expose
    @SerializedName("subscribed_date")
    private String subscribed_date;
    @Expose
    @SerializedName("subscribed")
    private int subscribed;
    @Expose
    @SerializedName("car_p_r")
    private String car_p_r;
    @Expose
    @SerializedName("car_p_f")
    private String car_p_f;
    @Expose
    @SerializedName("id_car")
    private String id_car;
    @Expose
    @SerializedName("id_card")
    private String id_card;
    @Expose
    @SerializedName("license")
    private String license;
    @Expose
    @SerializedName("birth_date")
    private String birth_date;
    @Expose
    @SerializedName("gender")
    private String gender;
    @Expose
    @SerializedName("id_number")
    private String id_number;

    public int getIs_sub_or_percen() {
        return is_sub_or_percen;
    }

    public void setIs_sub_or_percen(int is_sub_or_percen) {
        this.is_sub_or_percen = is_sub_or_percen;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getOrders_count_canceled() {
        return orders_count_canceled;
    }

    public void setOrders_count_canceled(int orders_count_canceled) {
        this.orders_count_canceled = orders_count_canceled;
    }

    public int getOrders_count() {
        return orders_count;
    }

    public void setOrders_count(int orders_count) {
        this.orders_count = orders_count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubscribed_ex_date() {
        return subscribed_ex_date;
    }

    public void setSubscribed_ex_date(String subscribed_ex_date) {
        this.subscribed_ex_date = subscribed_ex_date;
    }

    public int getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }

    public String getCar_p_r() {
        return car_p_r;
    }

    public void setCar_p_r(String car_p_r) {
        this.car_p_r = car_p_r;
    }

    public String getCar_p_f() {
        return car_p_f;
    }

    public void setCar_p_f(String car_p_f) {
        this.car_p_f = car_p_f;
    }

    public String getId_car() {
        return id_car;
    }

    public void setId_car(String id_car) {
        this.id_car = id_car;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getSubscribed_date() {
        return subscribed_date;
    }

    public void setSubscribed_date(String subscribed_date) {
        this.subscribed_date = subscribed_date;
    }
}
