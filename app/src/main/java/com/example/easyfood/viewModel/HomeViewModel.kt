package com.example.easyfood.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.pojo.*
import com.example.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoryItemsLiveData = MutableLiveData<List<Category>>()
    private var favMealsLiveData = mealDatabase.mealDao().selectMeal()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private var searchMealLiveData = MutableLiveData<List<Meal>>()

//    init {
//        getRandomMeal()
//    }

    private var saveInInternal: Meal ?= null

    // TODO: i need to create function to get the random meal from api
    fun getRandomMeal() {

        saveInInternal?.let {randomMeal ->
            randomMealLiveData.postValue(randomMeal)
            return
        }

        // TODO: i need to call the Retrofit instance and i need to connected the api
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {

                    // TODO: i need to make object to get the body from response
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                    saveInInternal = randomMeal

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("TAG", "onFailure: ${t.message.toString()}")
                }

            })
    }

    fun getCategoryItems() {
        RetrofitInstance.api.getCategoryItems().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {categoryList ->
                    categoryItemsLiveData.postValue(categoryList.categories)
                }
//                if (response.body() != null) {
//                    categoryItemsLiveData.value = response.body()!!.categories
//                } else {
//                    return
//                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }

    // TODO: i need to use my operation from Dao interface
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(meal)
        }
    }

    // TODO: i  need to make function to get the meal by id and view in bottom sheet
    fun getMealById(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()!!.meals.first()

                // TODO: that is check if meal != null
                meal?.let { meal->
                    bottomSheetMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun searchMeals(searchQuery: String){
        RetrofitInstance.api.searchMeal(searchQuery).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealsList = response.body()?.meals
                mealsList?.let {meal ->
                    searchMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message.toString()}", )
            }

        })
    }

    // TODO: i need to make function that is listen the live data when updated ( like observe in room database )
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoryItemsLiveData(): LiveData<List<Category>> {
        return categoryItemsLiveData
    }

    fun observeFavMealLiveData(): LiveData<List<Meal>>{
        return favMealsLiveData
    }

    fun observeBottomSheetMealLiveData(): LiveData<Meal>{
        return bottomSheetMealLiveData
    }

    fun observeSearchLiveData(): LiveData<List<Meal>>{
        return searchMealLiveData
    }
}