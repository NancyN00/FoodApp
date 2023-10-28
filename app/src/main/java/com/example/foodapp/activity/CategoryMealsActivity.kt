package com.example.foodapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapter.CategoriesMealAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoriesMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = CategoryMealsViewModel()
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        //when click beef you should see all types of beef
        //observe livedata
        categoryMealsViewModel.observeMealsLiveData().observe(this) { mealsList ->
            //mealsList.forEach { Log.d("test", it.strMeal) }

            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealList(mealsList)
        }

    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoriesMealAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}