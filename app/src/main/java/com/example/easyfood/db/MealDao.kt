package com.example.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.*

import com.example.easyfood.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal: Meal) // TODO: this function use update and insert

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("select * from mealInformation")
    fun selectMeal(): LiveData<List<Meal>>
}