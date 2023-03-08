package com.example.easyfood.db

import android.app.Application
import android.content.Context
import androidx.room.*
import com.example.easyfood.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase: RoomDatabase() {

    // TODO: i need to make instance from dao class
    abstract fun mealDao(): MealDao

//    companion object {
//        @Volatile // TODO: this is mean any change in instance from thread to visible any other thread
//        var instance: MealDatabase? = null
//
//        @Synchronized
//        fun getInstance(context: Context): MealDatabase {
//            // TODO: i need to check if the instance is null i need to make instance from database
//            if (instance == null) {
//                // TODO: fallbackToDestructiveMigration() => this is to rebuild the database
//                instance = Room.databaseBuilder(context, MealDatabase::class.java, "meal.db")
//                    .fallbackToDestructiveMigration().build()
//            }
//            return instance as MealDatabase
//        }
//    }

}