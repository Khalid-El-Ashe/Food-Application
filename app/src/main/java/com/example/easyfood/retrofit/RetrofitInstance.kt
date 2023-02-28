package com.example.easyfood.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

// TODO: this class to make instance from class MealApi
object RetrofitInstance {

    // TODO: i need to make build to the Retrofit instance
    val api: MealApi by lazy {
        // TODO: in this body i need to make build for the Retrofit
        Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(MealApi::class.java)
    }
}