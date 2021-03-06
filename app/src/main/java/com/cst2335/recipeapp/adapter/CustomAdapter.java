package com.cst2335.recipeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import recipe.app.model.Result;
import com.cst2335.recipeapp.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private ArrayList<Result> list;
    private ArrayList<Result> filteredList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public CustomAdapter(Context context, ArrayList<Result> resultArrayList, CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.list = resultArrayList;
        this.filteredList = resultArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredList.get(myViewHolder.getAdapterPosition()), myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder viewHolder, int position) {

        viewHolder.title.setText(filteredList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                filteredList = list;

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }
}
