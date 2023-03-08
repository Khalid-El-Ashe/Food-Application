package com.example.easyfood.viewModel

import android.content.Context
import androidx.room.Room
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.retrofit.MealApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // TODO: i need to make instance of my database when install the app
    @Singleton
    @Provides
    fun providerDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MealDatabase::class.java, "meal.db").build()

    // TODO: i need to make instance of my Retrofit
    @Singleton
    @Provides
    fun providerRetrofitInstanceApi(): MealApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.themealdb.com/api/json/v1/1/").build()
            .create(MealApi::class.java)
    }
}