package com.example.manusers.Utils;

import android.content.SharedPreferences;

import com.example.manusers.Models.User;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {
    private static String url="http://192.168.1.185/API/";

    public static Retrofit getRetrofit(){

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class,new MyDesencryptor());

        return new  Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                .build();
    }

    public static Retrofit getRetrofitGeneric(){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static int getIdUser(SharedPreferences preferences) {
        return preferences.getInt("id",0);
    }
}
