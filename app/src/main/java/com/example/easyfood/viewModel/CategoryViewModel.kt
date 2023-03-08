package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.pojo.Category
import com.example.easyfood.pojo.CategoryList
import com.example.easyfood.pojo.MealsByCategory
import com.example.easyfood.pojo.MealsByCategoryList
import com.example.easyfood.retrofit.MealApi
import com.example.easyfood.retrofit.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor (val mealApi: MealApi) : ViewModel() {

    private val categoryDetailsLiveData = MutableLiveData<List<MealsByCategory>>()


    // TODO: i need to get the random category and put it in the live data to update
    fun getMealsByCategory(categoryName: String) {
        mealApi.getMealsByCategories(categoryName).enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { mealsList ->
                    categoryDetailsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun observeCategoryDetailsLiveData(): LiveData<List<MealsByCategory>> {
        return categoryDetailsLiveData
    }
}