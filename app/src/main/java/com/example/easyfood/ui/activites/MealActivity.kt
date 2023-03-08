package com.example.easyfood.ui.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.pojo.Meal
import com.example.easyfood.ui.fragments.HomeFragment
import com.example.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var idMeal: String
    private lateinit var nameMeal: String
    private lateinit var thumbMeal: String
    private lateinit var youtubeLink: String

    // TODO: i need to make instance from the view model
    private val mealMvvM by viewModels<MealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: i need to make instance from database
//        val mealDatabase = MealDatabase.getInstance(this)
//        val viewModelFactory = MealViewModelFactory(mealDatabase)
//        mealMvvM = ViewModelProvider(this, viewModelFactory).get(MealViewModel::class.java)
        

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        // TODO: i need to get the meal details from view model
        mealMvvM.getMealDetails(idMeal)
        observeMealDetailsLiveData()

        onYoutubeImageClicked()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealFav?.let {
                mealMvvM.insertMeal(it)
                Toast.makeText(this, "success to add in favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClicked() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealFav: Meal? = null
    private fun observeMealDetailsLiveData() {
        mealMvvM.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                responseCase()

                val meal = t
                mealFav = meal

                binding.tvCategories.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructionSteps.text = meal.strInstructions

                youtubeLink = meal.strYoutube!!
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext).load(thumbMeal).into(binding.imgMealDetail)
        binding.collapsingToolbar.title = nameMeal

        // TODO: i need to change the color when i scroll the collapsingToolbar
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        idMeal = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        nameMeal = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        thumbMeal = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvCategories.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun responseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvCategories.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}