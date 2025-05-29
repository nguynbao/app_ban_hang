package com.example.app_ban_hang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.R;

import java.util.List;

public class adapter_banner extends RecyclerView.Adapter<adapter_banner.ViewHolder> {
    private List<Integer> bannerList;
    public adapter_banner(List<Integer> bannerList){
        this.bannerList = bannerList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView banner;
        public ViewHolder(View view){
            super(view);
            banner = view.findViewById(R.id.banner);
        }
    }
    @NonNull
    @Override
    public adapter_banner.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull adapter_banner.ViewHolder holder, int position) {
        holder.banner.setImageResource(bannerList.get(position));
    }
    @Override
    public int getItemCount() {
        return bannerList.size();
    }

}
