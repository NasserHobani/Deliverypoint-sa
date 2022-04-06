package com.mndoobk_d.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverDataModel {

    @Expose
    @SerializedName("driverdata")
    private Driverdata driverdata;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("phone")
    private int phone;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("a_name")
    private String a_name;

    public Driverdata getDriverdata() {
        return driverdata;
    }

    public void setDriverdata(Driverdata driverdata) {
        this.driverdata = driverdata;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }
}
