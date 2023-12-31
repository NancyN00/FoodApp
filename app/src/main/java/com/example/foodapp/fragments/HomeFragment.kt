package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activity.CategoryMealsActivity
import com.example.foodapp.activity.MealActivity
import com.example.foodapp.adapter.CategoriesAdapter
import com.example.foodapp.adapter.MostPopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val  MEAL_ID = "com.example.foodapp.fragments.idMeal"
        const val  MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
        const val  MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME ="com.example.foodapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //homeMvvm = ViewModelProvider.of(this)[HomeViewModel::class.java]

        homeMvvm = HomeViewModel()
        popularItemsAdapter = MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomClickMeal()

        homeMvvm.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {category ->
            //when we click to move to a new activity from this activity
            val intent = Intent(activity,CategoryMealsActivity::class.java)

            //now, pass the name of category to the activity to be searched all the meal in category inside activity

            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }



    private fun prepareCategoriesRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }

    }

    private fun observeCategoriesLiveData() {
       homeMvvm.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories->
           categoriesAdapter.setCategoryList(categories)

           //check if there is a list of the categories
           //  categories.forEach {category -> Log.d("test", category.strCategory )}
       })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {meal->
            //the meal has a name, id, and image
            //move from this activity to Meal activity to display the information of that meal

            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            //set adapter inside rv adapter
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)

        }
    }

    private fun onRandomClickMeal() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner
        ) { value -> //onchange method will give the meal
            //the meal is in variable value
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.imgRandomMeal)

            //assign value to random meal
            this.randomMeal = value
        }
    }


}