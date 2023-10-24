package com.example.foodapp.retrofit

import com.example.foodapp.pojo.Meal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    //it will initialize the value
    //the converter will take the json file and convert to kotlin object


    val api:MealApi by lazy {
        //build the Mealapi

        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)

    }
}