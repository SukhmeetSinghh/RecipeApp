package com.cst2335.recipeapp.screens;

import static com.cst2335.recipeapp.network.Constant.TABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import com.cst2335.recipeapp.model.Result;
import com.cst2335.recipeapp.R;
import com.cst2335.recipeapp.adapter.CustomAdapter;
import com.cst2335.recipeapp.adapter.CustomItemClickListener;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<Result> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        initWidgets();

    }

    // init all widgets
    private void initWidgets() {
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onResume() {
        getSavedList();
        super.onResume();
    }

    //get all save recipe from database
    private void getSavedList() {
        SharedPreferences mPrefs = getSharedPreferences(TABLE, MODE_PRIVATE);
        Map<String, ?> keys = mPrefs.getAll();

        list = new ArrayList<>();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Gson gson = new Gson();
            try {
                String json = mPrefs.getString(entry.getKey(), "");
                Result result = gson.fromJson(json, Result.class);
                list.add(result);
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
        }
        setAdapter(list);
    }

    // set adapter data and move to next details screen with delete button
    void setAdapter(ArrayList<Result> list) {
        customAdapter = new CustomAdapter(getApplicationContext(), list, new CustomItemClickListener() {
            @Override
            public void onItemClick(Result result, int position) {
                Intent intent = new Intent(FavoriteActivity.this, com.cst2335.recipeapp.screens.DetailsActivity.class);
                intent.putExtra("result", result);
                intent.putExtra("from", "Delete");
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(customAdapter);
    }
}