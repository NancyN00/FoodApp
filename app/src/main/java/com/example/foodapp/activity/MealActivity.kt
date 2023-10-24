package com.example.foodapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealMvvm:MealViewModel
    private lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = MealViewModel()

        getMealInformationFromIntent()

        setInformationInViews()

        loadingCase()



        mealMvvm.getMealDetails(mealId)
        observeMealDetailsLiveData()

        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value

                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvCategory.text = "Area: ${meal!!.strArea}"
                binding.tvCategory.text = meal.strInstructions

            youtubeLink = meal.strYoutube}


        })
    }

    private fun setInformationInViews() {
        //use glide to set image inside image view
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)


        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))



    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    //loading from api
    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility= View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE

    }

    private  fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility= View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
    }
}