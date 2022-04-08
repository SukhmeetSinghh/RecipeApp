package com.cst2335.recipeapp.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.recipeapp.R;
import com.cst2335.recipeapp.adapter.CustomAdapter;

import java.util.ArrayList;

import javax.xml.transform.Result;

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
    //init all widgets
    private void initWidgets() {
        recyclerView=findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
}

@Override
protected void onResume() {
    getSavedList();
    super.onResume();}
}

//get all save recipe from database