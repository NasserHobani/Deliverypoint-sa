package com.mndoobk_d.api;


import com.mndoobk_d.model.DataResponse;
import com.mndoobk_d.model.DriverDataModel;
import com.mndoobk_d.model.LoginResponse;
import com.mndoobk_d.model.OrderDataModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String Password,@Field("cat_id") int cat_id);

    @Headers("Accept: application/json")
    @GET("user/{id}")
    Call<DataResponse<DriverDataModel>> getDriverData(@Header("Authorization") String Authorization, @Path("id") int id);

    @Headers("Accept: application/json")
    @GET("orderdata")
    Call<DataResponse<OrderDataModel>> getOrder(@Header("Authorization") String Authorization);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("orderdata/{orderdatum}")
    Call<DataResponse<OrderDataModel>> closeorder(@Header("Authorization") String Authorization,@Field("status") String status,@Path("orderdatum") int orderdatum);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("receivemobile")
    Call<DataResponse<OrderDataModel>> receive(@Header("Authorization") String Authorization,@Field("order_id") int order_id);
}
