package com.cst2335.recipeapp;

import static recipe.app.network.Constant.API_KEY;
import static recipe.app.network.Constant.TABLE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.recipeapp.adapter.CustomAdapter;
import com.cst2335.recipeapp.adapter.CustomItemClickListener;
import com.cst2335.recipeapp.helper.DialogHelper;
import com.cst2335.recipeapp.model.ResponseData;
import com.cst2335.recipeapp.network.RetrofitClient;
import com.cst2335.recipeapp.network.RetrofitInterface;
import com.cst2335.recipeapp.screens.DetailsActivity;
import com.cst2335.recipeapp.screens.FavoriteActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

import recipe.app.adpater.CustomAdapter;
import recipe.app.adpater.CustomItemClickListener;
import recipe.app.helper.DialogHelper;
import recipe.app.network.RetrofitClient;
import recipe.app.network.RetrofitInterface;
import recipe.app.screens.FavoriteActivity;
import recipe.app.screens.DetailsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import recipe.app.model.Result;
import recipe.app.model.ResponseData;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private TextView favorites, lastSearch;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ImageView removeLastValue;

    private ArrayList<Result>list;
    private String header = "application/json";
    private String maxValue = "15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        setClickListener();
        recycleViewInit();
        getLastSearch();

        //First time api call without any search text
        getListFromRestApi("");
    }

    // recycle view init

    private void recycleViewInit(){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    // All click listener

    private void setClickListener(){
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
                startActivity(intent);
            }
        });
    lastSearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (lastSearch.getText().toString().length()> 0){
                editTextSearch.setText(lastSearch.getText().toString());
            }
        }
    });
    removeLastValue.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastSearch.setText("");
            removeLastValue.setVisibility(View.GONE);
        }
    });
        editTextSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                getListFromRestApi(editTextSearch.getText().toString());
                SharedPreferences.Editor editor = getSharedPreferences(TABLE, MODE_PRIVATE).edit();
                editor.putString("lastSearch", editTextSearch.getText().toString());
                editor.commit();

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }
    // Get last search item

    private void getLastSearch(){
        SharedPreferences mPrefs = getSharedPreferences(TABLE, MODE_PRIVATE);
        String lat = mPrefs.getString("lastSearch", "");
        lastSearch.setText(last);
        if (last.equals("")){
            removeLastValue.setVisibility(View.GONE);
        }
    }
    //Api call for get recipe data

    private void getListFromRestApi(String search){
        progressDialog = createProgressDialog(MainActivity.this);
        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
            Call<ResponseData> call = retrofitInterface.getResult(header, API_KEY, search, maxValue);
            call.enqueue(new Window.Callback<ResponseData>()){
                @Override
                public void onResponse(Call<ResponseData>call,Response<ResponseData>response{
                    progressDialog.dismiss();
                    list = new ArrayList<>(response.body().getResults());
                    customAdapter = new CustomAdapter(getApplicationContext(), list, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(Result result, int position) {
                            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                            intent.putExtra("results", results);
                            intent.putExtra("from", "Save");
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(customAdapter);
            }
            @Override
                    public void onFailure(Call<ResponseData>call,Throwable t){
                    progressDialog.dismiss();
                DialogHelper.getAlertWithMessage("Hata",t.getMessage(),MainActivity.this);
            }
        });
    }
    //progress dialog
    public ProgressDialog createProgressDialog(Context mContext){
        ProgressDialog dialog = new ProgressDialog(mContext);
        try{
            dialog.show();
        }catch (WindowManager.BadTokenException e){
            Log.e("Exception", e.toString());
        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_layout);
        return dialog;
    }
}