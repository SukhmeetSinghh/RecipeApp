package com.cst2335.recipeapp.network;


import com.cst2335.recipeapp.model.ResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


//https://api.spoonacular.com/recipes/findByIngredients?ingredients=apples,+flour,+sugar&number=2
public interface RetrofitInterface {
    @GET("recipes/complexSearch")
    Call<ResponseData> getResult(
            @Header("Content-Type") String header,
            @Query("apiKey") String apiKey,
            @Query("query") String name1,
            @Query("number") String name2);
}
