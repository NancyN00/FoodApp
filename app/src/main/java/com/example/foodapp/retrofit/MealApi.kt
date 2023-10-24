package com.example.foodapp.retrofit

import com.example.foodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface MealApi {

    //get data from api
    //retrofit will generate all the code needed

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails() : Call<MealList>
}




