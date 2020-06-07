package com.example.manusers.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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


public class ListUserFragment extends Fragment {
    private View rootView;
    private MyAdapter adapter;
    private ListView listView;
    private List<User> List;
    private ListListener callback;
    private Context cont;

    private SharedPreferences preferences;


    public ListUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            cont = context;
            callback = (ListListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_list_user, container, false);

        preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);


        listView = rootView.findViewById(R.id.listUser);

        IUsers services = Util.getRetrofitGeneric().create(IUsers.class);

        Call<List<User>> listCall = services.getUsers();

        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List = response.body();
                if (List != null) {
                    adapter = new MyAdapter(R.layout.adapter_layout, getContext(), List);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        registerForContextMenu(listView);

        return rootView;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.item_delete){
            deleteUser(this.List.get(info.position));
        }else {
            updateUser(this.List.get(info.position));
        }
        return super.onContextItemSelected(item);
    }

    private void deleteUser(final User user){
        IUsers services = Util.getRetrofitGeneric().create(IUsers.class);
        Call<String> userCall = services.deleteUser(user.getId());
        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.message().equals("OK")) {
                    Toast.makeText(getContext(),"User delete successfully",Toast.LENGTH_SHORT).show();
                    List.remove(user);
                    adapter.notifyDataSetChanged();
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

    private void updateUser(User user){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id",user.getId());
        editor.apply();
        callback.changeFragForm();
    }

    public interface ListListener {
        void changeFragForm();
    }
}
