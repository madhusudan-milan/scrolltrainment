package com.example.scrolltrainment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    Context context;
    ArrayList<Video> arrayList;

    onItemclickListner onItemclickListner;

    public VideoAdapter(Context context, ArrayList<Video> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_of_videoitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getLink()).centerCrop().into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {

            onItemclickListner.onClick(arrayList.get(position));

        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            imageView= itemView.findViewById(R.id.thumbnail_image);


        }
    }

    public void setOnItemclickListner(VideoAdapter.onItemclickListner onItemclickListner) {
        this.onItemclickListner = onItemclickListner;
    }

    public interface onItemclickListner{
        void onClick(Video video);
    }
}
