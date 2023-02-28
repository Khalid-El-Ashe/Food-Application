package com.example.easyfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.adapters.CategoryAdapter
import com.example.easyfood.adapters.MostPopularAdapter
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.pojo.Category
import com.example.easyfood.pojo.MealsByCategory
import com.example.easyfood.pojo.Meal
import com.example.easyfood.ui.activites.CategoryActivity
import com.example.easyfood.ui.activites.MainActivity
import com.example.easyfood.ui.activites.MealActivity
import com.example.easyfood.ui.fragments.bottomSheet.MealBottomSheetFragment
import com.example.easyfood.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    // TODO: i need to make static extra keys intents
    companion object {
        const val MEAL_ID = "com.example.easyfood.ui.fragments.idMeal"
        const val MEAL_NAME = "com.example.easyfood.ui.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.easyfood.ui.fragments.thumbMeal"

        const val CATEGORY_ID = "com.example.easyfood.ui.fragments.idCategory"
        const val CATEGORY_NAME = "com.example.easyfood.ui.fragments.nameCategory"
        const val CATEGORY_THUMB = "com.example.easyfood.ui.fragments.thumbCategory"
    }

    // TODO: i need to make instance of view model class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: in here i need to get view model from main class i well make instance
        viewModel = (activity as MainActivity).viewModel
        popularAdapter = MostPopularAdapter()
        categoryAdapter = CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularItemsRecyclerView()
        categoryItemsRecyclerView()

        viewModel.getRandomMeal()
        observeRandomMeal()

        onRandomMealClick()
        onPopularItemClick()
        onCategoryItemClick()
        onPopularItemLongClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()

        viewModel.getCategoryItems()
        observeCategoryItemsLiveData()

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularAdapter.onLongClick = { meal ->
            // TODO: i need to make instance from bottom sheet class
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun observeCategoryItemsLiveData() {
        viewModel.observeCategoryItemsLiveData().observe(viewLifecycleOwner, { categorys ->
            // TODO: i need to use forech to get all categoris
            categoryAdapter.setCategories(categorys as ArrayList<Category>)
        })
    }

    private fun categoryItemsRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun popularItemsRecyclerView() {
        binding.rvViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner, { mealList ->
            popularAdapter.setMeals(mealList as ArrayList<MealsByCategory>)
        })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    // TODO: in this function i need to listen the live data to update
    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(
            viewLifecycleOwner, { meal ->
                Glide.with(requireView()).load(meal!!.strMealThumb).centerCrop()
                    .into(binding.imgRandomMeal)

                // TODO: i need to save the meal values to i can share it
                this.randomMeal = meal
            })
    }
}