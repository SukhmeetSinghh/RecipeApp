package com.cst2335.recipeapp.adapter;

import java.util.ArrayList;
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
import recipe.app.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable{

    private ArrayList<Result> list;
    private ArrayList<Result> filteredList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public CustomAdapter(Context context, ArrayList<Result> result ArrayList, CustomItemClickListener customItemClickListener)
    {
        this.context=context;
        this.list = resultArrayList;
        this.filteredList = resultArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                customItemClickListener.onItemClick(filteredList.get(myViewHolder.getAdapterPosition()), myViewHolder.getAdapterPosition());
            }

        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder viewHolder, int position)
    {
        viewHolder.title.setText(filteredList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter()
    {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                filteredList =list;

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;

            }



        }
    }

}
