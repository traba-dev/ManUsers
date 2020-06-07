package com.example.manusers.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.manusers.Fragments.FormFragment;
import com.example.manusers.Fragments.ListUserFragment;
import com.example.manusers.Interfaces.IUsers;
import com.example.manusers.Models.User;
import com.example.manusers.R;
import com.example.manusers.Utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FormFragment.DataListener, ListUserFragment.ListListener {

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        ListUserFragment listFragment = new ListUserFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,listFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_form){
            cleanShared();
            FormFragment formFragment = new FormFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,formFragment).commit();
        }else {
            ListUserFragment listFragment = new ListUserFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,listFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void cleanShared() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void changeFrag() {
        ListUserFragment listFragment = new ListUserFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,listFragment).commit();
    }

    @Override
    public void changeFragForm() {
        FormFragment formFragment = new FormFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,formFragment).commit();
    }
}
