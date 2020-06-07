package com.example.manusers.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manusers.Adapter.MyAdapter;
import com.example.manusers.Interfaces.IUsers;
import com.example.manusers.Models.User;
import com.example.manusers.R;
import com.example.manusers.Utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {
    private View rooView;
    private DataListener callback;
    private Context cont;
    private User user;
    private SharedPreferences preferences;

    private boolean flag = false;

    private EditText name;
    private EditText lastName;
    private EditText phone;
    private EditText email;
    private EditText pass;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            cont = context;
            callback = (DataListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rooView = inflater.inflate(R.layout.fragment_form, container, false);
        preferences = getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);

        if (Util.getIdUser(preferences) > 0)
            setUserInView(Util.getIdUser(preferences));

        name = rooView.findViewById(R.id.editName);
        lastName = rooView.findViewById(R.id.editLastName);
        phone = rooView.findViewById(R.id.editPhone);
        email = rooView.findViewById(R.id.editEmail);
        pass = rooView.findViewById(R.id.editPass);
        Button Save = rooView.findViewById(R.id.btnRegister);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(lastName.getText()) && !TextUtils.isEmpty(phone.getText()) && !TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(pass.getText()))
                {
                    long _phone = Long.parseLong(phone.getText().toString());
                    User user = new User(0,name.getText().toString(),lastName.getText().toString(),_phone,email.getText().toString(),pass.getText().toString());
                    if (!flag)
                        sendUser(user);
                    else
                        updateUser(user);

                }else {
                    Toast.makeText(getContext(),"Todos los campos son requeridos",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rooView;
    }

    private void updateUser(User user) {
        user.setId(this.user.getId());
        IUsers services = Util.getRetrofitGeneric().create(IUsers.class);
        Call<String> updateCall = services.updateUser(user);
        updateCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200){
                    Toast.makeText(getContext(),"User updated Successfully",Toast.LENGTH_SHORT).show();
                    callback.changeFrag();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserInView(int idUser) {
        IUsers services = Util.getRetrofitGeneric().create(IUsers.class);
        Call<User> callUser = services.getUserById(idUser);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200){
                    User _user = response.body();
                    setFieldsData(_user);
                }else {
                    Toast.makeText(getContext(),"User not found",Toast.LENGTH_SHORT).show();
                    callback.changeFrag();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    private void sendUser(User user){
        IUsers services = Util.getRetrofitGeneric().create(IUsers.class);
        Call<String> userCall = services.createUser(user);
        userCall.enqueue(new Callback<String>() {
               @Override
               public void onResponse(Call<String> call, Response<String> response) {

                   if (response.message().equals("OK")) {
                       Toast.makeText(getContext(),"User created successfully",Toast.LENGTH_SHORT).show();
                       callback.changeFrag();
                   } else {
                       Toast.makeText(getContext(),response.message(),Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onFailure(Call<String> call, Throwable t) {
                   Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
               }
           });
    }

    private void setFieldsData(User user) {
        this.flag = true;
        this.user = user;
        name.setText(user.getName());
        lastName.setText(user.getLastname());
        phone.setText(String.valueOf(user.getPhone()));
        email.setText(user.getEmail());
        pass.setText(user.getPass());

        email.setEnabled(false);
        pass.setEnabled(false);
    }

    private void sendText() {
        callback.changeFrag();
    }

    public interface DataListener {
        void changeFrag();
    }

}
