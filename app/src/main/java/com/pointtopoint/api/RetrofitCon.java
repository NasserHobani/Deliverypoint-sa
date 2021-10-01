package com.pointtopoint.api;

import android.content.Context;


import com.pointtopoint.db.DatabaseHandler;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCon {
    private static Retrofit retrofit;
    private static String BASE_URL ="https://deliverypoint-sa.com/api/";
    private static APIInterface api;
    private Context context;

    DatabaseHandler db;
    public RetrofitCon(Context context){
        this.context = context;
        db = new DatabaseHandler(context);
//        if (db.getCountApi() > 0){
//
//            BASE_URL = db.getApi();
//            String b = String.valueOf(BASE_URL.charAt(BASE_URL.length() -1));
//            if (!b.equals("/")) {
//
//                BASE_URL = BASE_URL+"/";
//            }
//
//        }else {
//            Toast.makeText(context,"يرجى تحديد السيرفر",Toast.LENGTH_LONG).show();
//        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(APIInterface.class);
    }

    public APIInterface getService(){

        return api;
    }
}
