package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.foodapp.activity.MealActivity
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal

    companion object{
        const val  MEAL_ID = "com.example.foodapp.fragments.idMeal"
        const val  MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
        const val  MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //homeMvvm = ViewModelProvider.of(this)[HomeViewModel::class.java]

        homeMvvm = HomeViewModel()

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

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomClickMeal()

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