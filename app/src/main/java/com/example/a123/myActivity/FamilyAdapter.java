package com.example.a123.myActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a123.R;
import com.example.a123.myClass.Family;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {

    private List<Family> mFamilyList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView nameC, nameE;
        LinearLayout llBg;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            nameC = (TextView) view.findViewById(R.id.fam_nc);
            nameE = (TextView) view.findViewById(R.id.fam_ne);
            llBg = (LinearLayout) view.findViewById(R.id.fam_ll);
        }
    }

    public FamilyAdapter(List<Family> familyList) {
        mFamilyList = familyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_item, parent, false);
        view.getBackground().setAlpha(204);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Family family = mFamilyList.get(position);
        holder.nameC.setText(family.getNameC());
        holder.nameE.setText(family.getNameE());
        holder.llBg.setBackgroundResource(family.getImageRes());
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
        return mFamilyList.size();
    }

    public void remove(int position) {
        mFamilyList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mFamilyList.size() - position);
    }

    public void add(int position, Family family) {
        mFamilyList.add(position, family);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mFamilyList.size() - position);
    }

    public void change(int position, Family family) {
        mFamilyList.remove(position);
        mFamilyList.add(position, family);
        notifyItemChanged(position);
    }

}
