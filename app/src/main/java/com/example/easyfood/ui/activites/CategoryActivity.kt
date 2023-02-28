package com.example.easyfood.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.adapters.CategoryMealAdapter
import com.example.easyfood.databinding.ActivityCategoryBinding
import com.example.easyfood.pojo.MealsByCategory
import com.example.easyfood.ui.fragments.HomeFragment
import com.example.easyfood.viewModel.CategoryViewModel

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryMvvM: CategoryViewModel
    private lateinit var categoryMealAdapter: CategoryMealAdapter
    private lateinit var nameCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealAdapter = CategoryMealAdapter()

        // TODO: i need to make instnace of class viewmodel
        categoryMvvM = ViewModelProvider(this).get(CategoryViewModel::class.java)

        getCategoryInformationFromIntent()
        observeMealDetailsLiveData()
        getMealsByCategory()
        preperRecyclerView()
    }

    private fun preperRecyclerView() {
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(this@CategoryActivity, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealAdapter
        }
    }

    private fun getCategoryInformationFromIntent() {
        val intent = intent
        nameCategory = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
    }

    private fun observeMealDetailsLiveData() {
        categoryMvvM.observeCategoryDetailsLiveData().observe(this, Observer { mealsList ->
            binding.tvCategoryCount.text =  "$nameCategory: ${mealsList.size}"
            categoryMealAdapter.setMealCategories(mealsList as ArrayList<MealsByCategory>)
        })
    }

    private fun getMealsByCategory(){
        categoryMvvM.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
    }
}