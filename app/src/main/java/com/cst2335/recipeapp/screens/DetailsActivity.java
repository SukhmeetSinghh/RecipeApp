package com.cst2335.recipeapp.screens;

import static com.cst2335.recipeapp.network.Constant.TABLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.cst2335.recipeapp.model.Result;
import com.cst2335.recipeapp.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewId, textViewFat, textViewProtein;
    private Button buttonSave;
    private String buttonText;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);

        initWidgets();
        getIntentData();
        setClickListener();
    }

    // init all widgets
    private void initWidgets() {
        textViewId = findViewById(R.id.textViewId);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewFat = findViewById(R.id.textViewFat);
        textViewProtein = findViewById(R.id.textViewProtein);
        buttonSave = findViewById(R.id.buttonSave);
    }

    // get data from previous activity and set into current screen
    private void getIntentData() {
        Intent intent = getIntent();
        result = (Result) intent.getSerializableExtra("result");
        buttonText = intent.getStringExtra("from");

        textViewId.setText(result.getId() + "");
        textViewTitle.setText(result.getTitle());
        textViewFat.setText(result.getFat());
        textViewProtein.setText(result.getProtein());
        buttonSave.setText(buttonText);
    }

    // all click listener
    private void setClickListener() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonText.equalsIgnoreCase("save")) {
                    SharedPreferences.Editor editor = getSharedPreferences(TABLE, MODE_PRIVATE).edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(result);
                    editor.putString(result.getId() + "", json);
                    editor.commit();
                    Toast.makeText(DetailsActivity.this, "Recipe Saved!!", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences.Editor editor = getSharedPreferences(TABLE, MODE_PRIVATE).edit();
                    editor.remove(result.getId() + "").commit();
                    Toast.makeText(DetailsActivity.this, "Recipe Deleted!!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

}