package com.example.easyfood.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMainBinding
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.viewModel.HomeViewModel
import com.example.easyfood.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    // TODO: i need to make viewmodel in this class to use it in all my fragments because i don't need to make more instance from database
    val viewModel: HomeViewModel by lazy {
        // TODO: i need to make instance of database
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory).get(HomeViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.host_fragment)
        binding.btmNav.setupWithNavController(navController)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when(destination.id){
//
//            }
//        }
    }
}