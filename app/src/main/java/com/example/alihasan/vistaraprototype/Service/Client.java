package com.example.alihasan.vistaraprototype.Service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Client {

    @POST("authorize.php")
    @FormUrlEncoded
    Call<String> getAuth(@Field("EMAIL") String ID, @Field("PASSWORD") String PASS);

    @POST("addtotable.php")
    @FormUrlEncoded
    Call<String> flightData(@Field("EMAIL") String EMAIL,
                            @Field("AIRCRAFT") String AIRCRAFT,
                            @Field("HOURS") String HOURS);
}
