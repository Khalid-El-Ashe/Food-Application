package com.example.easyfood.retrofit

import com.example.easyfood.pojo.CategoryList
import com.example.easyfood.pojo.MealsByCategoryList
import com.example.easyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    // TODO: in here i need to get the random meal from api
    // TODO: https://www.themealdb.com/api/json/v1/1/random.php
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    // TODO: i need to get the full meal details
    // TODO: https://www.themealdb.com/api/json/v1/1/lookup.php?i=52772 
    @GET("lookup.php")
    fun getMealDetails(
        @Query("i") id: String
    ): Call<MealList>

    // TODO: i need to get the images meal
    // TODO: https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php")
    fun getPopularItems(
        @Query("c") categryName: String
    ): Call<MealsByCategoryList>

    // TODO: i need to get the category items  
    // TODO: https://www.themealdb.com/api/json/v1/1/categories.php
    @GET("categories.php")
    fun getCategoryItems(): Call<CategoryList>

    // TODO: i need to get the count of category items
    // TODO: www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php")
    fun getMealsByCategories(
        @Query("c") categoryName: String
    ): Call<MealsByCategoryList>

    // TODO: i need to search the meal from api
    // TODO: www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    @GET("search.php")
    fun searchMeal(@Query("s") searchQuery: String): Call<MealList>
}