package com.example.manusers.Utils;

import com.example.manusers.Models.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MyDesencryptor implements JsonDeserializer<User> {




    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        int id ;
        String name;
        String lastname;
        long phone;
        String email;
        String pass;



        return null;
    }
}
