package com.example.a123.myActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a123.R;
import com.example.a123.myClass.Genera;

import java.util.List;

public class GeneraAdapter extends RecyclerView.Adapter<GeneraAdapter.ViewHolder>  {

    private List<Genera> mGeneraList;
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

    public GeneraAdapter(List<Genera> generaList) {
        mGeneraList = generaList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_genera, parent, false);
        //view.getBackground().setAlpha(204);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Genera genera = mGeneraList.get(position);
        holder.name.setText(genera.getName());
        holder.like.setImageResource(genera.isLike()?R.mipmap.like:R.mipmap.dislike);
        holder.image.setImageResource(genera.getImageRes());
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
        return mGeneraList.size();
    }

    public void remove(int position) {
        mGeneraList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mGeneraList.size() - position);
    }

    public void add(int position, Genera genera) {
        mGeneraList.add(position, genera);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mGeneraList.size() - position);
    }

    public void change(int position, Genera genera) {
        mGeneraList.remove(position);
        mGeneraList.add(position, genera);
        notifyItemChanged(position);
    }
}
