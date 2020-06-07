package com.example.manusers.Interfaces;

import com.example.manusers.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IUsers {

    @GET("getUsers")
    Call<List<User>> getUsers();

    @Headers("Content-Type: application/json")
    @POST("addUser")
    Call<String> createUser(@Body User user);

    @Headers("Content-Type: application/json")
    @POST("updateUser")
    Call<String> updateUser(@Body User user);

    @Headers("Content-Type: application/json")
    @POST("deleteUser")
    Call<String> deleteUser(@Query("id") int id);

    @GET("getUserById")
    Call<User> getUserById(@Query("id") int id);

}
