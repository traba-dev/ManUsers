package com.example.manusers.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.manusers.Models.User;
import com.example.manusers.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private int layout;
    private Context context;
    private List<User> userList;

    public MyAdapter(int layout, Context context, List<User> userList) {
        this.layout = layout;
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        IViewHolder IV;

        if (convertView == null) {
           IV = new IViewHolder();
           convertView = LayoutInflater.from(this.context).inflate(this.layout, null);
           IV.name = convertView.findViewById(R.id.name);
            IV.lastName = convertView.findViewById(R.id.lastName);
            IV.phone = convertView.findViewById(R.id.phone);
            IV.email = convertView.findViewById(R.id.email);
            IV.pass = convertView.findViewById(R.id.pass);
            convertView.setTag(IV);

        }else{
            IV = (IViewHolder) convertView.getTag();
        }

        IV.name.setText(this.userList.get(position).getName());
        IV.lastName.setText(this.userList.get(position).getLastname());

        String phone;
        phone = String.valueOf(this.userList.get(position).getPhone());
        IV.phone.setText(phone);
        IV.email.setText(this.userList.get(position).getEmail());
        IV.pass.setText(this.userList.get(position).getPass());
        return convertView;
    }

     private static class IViewHolder{
        private TextView name;
        private TextView lastName;
        private TextView phone;
        private TextView email;
        private TextView pass;
    }
}
