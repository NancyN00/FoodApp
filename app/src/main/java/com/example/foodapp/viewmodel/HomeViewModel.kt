package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object  : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                //retrofit is connected to API. Get random meal and show in imageview
                //response body will return a meal list, the meal list has meals, and meals is a list of meal
                //have one meal, meaning, index of 0, because only one meal. add !!, not null
                //to test if we get the info, use Log.d
                //use glide to catch image from internet and show in imageview
                if(response.body() !=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                     randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            //t.message... will tell you why the connection failed
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }
        })
    }

    //this public function help listen random meal livedata in home fragment
    //you can change the value of Mutablelivedata because you have function (value)...
    //but on livedata, only you can read its livedata

    fun observeRandomMealLivedata(): LiveData<Meal>{
        return randomMealLiveData
    }
}