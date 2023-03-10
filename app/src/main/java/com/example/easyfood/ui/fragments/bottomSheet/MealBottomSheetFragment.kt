package com.example.easyfood.ui.fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.FragmentMealBottomSheetBinding
import com.example.easyfood.ui.activites.MainActivity
import com.example.easyfood.ui.activites.MealActivity
import com.example.easyfood.ui.fragments.HomeFragment
import com.example.easyfood.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

private const val MEAL_ID = "mealId"

@AndroidEntryPoint
class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMealBottomSheetBinding
    private var mealId: String? = null
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
//        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }

        observeBottomSheetMeal()
        onBottomSheetClick()
    }

    private fun onBottomSheetClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null){
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName: String? = null
    private var mealThumb: String? = null
    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMealLiveData().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetArea.text = meal.strArea
            binding.tvBottomSheetCategory.text = meal.strCategory
            binding.tvBottomSheetMealName.text = meal.strMeal
            binding.tvBottomSheetMealDetails.text = meal.strInstructions

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(mealID: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, mealID)
                }
            }
    }
}