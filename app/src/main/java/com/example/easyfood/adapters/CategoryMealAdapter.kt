package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.MealItemBinding
import com.example.easyfood.pojo.Category
import com.example.easyfood.pojo.MealsByCategory

class CategoryMealAdapter() : RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewHolder>() {

   private var mealsList = ArrayList<MealsByCategory>()
    var onItemClick: ((MealsByCategory) -> Unit)? = null

    fun setMealCategories(mealList: ArrayList<MealsByCategory>) {
        this.mealsList = mealList
        notifyDataSetChanged() // TODO: this is to refresh the adapter and update the views
    }

    inner class CategoryMealViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meals: MealsByCategory) {
            Glide.with(itemView).load(meals.strMealThumb).into(binding.imgMealCategory)
            binding.tvMealName.text = meals.strMeal
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryMealViewHolder {
        return CategoryMealViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealAdapter.CategoryMealViewHolder, position: Int) {
        val meal = mealsList[position]
        holder.bind(meal)
    }
}