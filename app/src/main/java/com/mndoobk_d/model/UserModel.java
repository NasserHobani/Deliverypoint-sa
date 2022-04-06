package com.mndoobk_d.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {


    @Expose
    @SerializedName("updated_at")
    private String updated_at;
    @Expose
    @SerializedName("created_at")
    private String created_at;
    @Expose
    @SerializedName("accessToken")
    private String accessToken;
    @Expose
    @SerializedName("group_id")
    private int group_id;
    @Expose
    @SerializedName("job_name")
    private String job_name;
    @Expose
    @SerializedName("zone_id")
    private int zone_id;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("a_name")
    private String nameA;


    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id")
    private int id;

    private String token;

    public UserModel() {
    }

    public UserModel(String accessToken, int id, String job_name, String nameA,String email) {
        this.accessToken = accessToken;
        this.id = id;
        this.job_name = job_name;
        this.nameA = nameA;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }
}
