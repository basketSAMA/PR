package com.example.a123.myActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a123.R;
import com.example.a123.myClass.BitmapUtil;
import com.example.a123.myClass.Plant;
import com.example.a123.myService.LoginService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class PlantListAdapter extends RecyclerView.Adapter<PlantListAdapter.ViewHolder>  {

    private List<Plant> mPlantList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        ImageView image, like;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.genera_card);
            name = (TextView) view.findViewById(R.id.genera_name);
            image = (ImageView) view.findViewById(R.id.genera_image);
            like = (ImageView) view.findViewById(R.id.genera_like);
        }
    }

    public PlantListAdapter(List<Plant> plantList) {
        mPlantList = plantList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plant_list, parent, false);
        //view.getBackground().setAlpha(204);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Plant plant = mPlantList.get(position);
        holder.name.setText(plant.getName());
        holder.like.setImageResource(plant.isLike()?R.mipmap.like:R.mipmap.dislike);
        //holder.image.setImageResource(plant.getImageRes());
        holder.image.setImageBitmap(plant.getImageBitmap());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return mPlantList.size();
    }

    public void remove(int position) {
        mPlantList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mPlantList.size() - position);
    }

    public void add(int position, Plant plant) {
        mPlantList.add(position, plant);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mPlantList.size() - position);
    }

    public void change(int position, Plant plant) {
        mPlantList.remove(position);
        mPlantList.add(position, plant);
        notifyItemChanged(position);
    }
}
