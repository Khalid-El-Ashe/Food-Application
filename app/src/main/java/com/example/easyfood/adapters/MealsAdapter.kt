package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.MealItemBinding
import com.example.easyfood.pojo.Meal

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavViewHolder>() {

    inner class FavViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            Glide.with(itemView).load(meal.strMealThumb).into(binding.imgMealCategory)
            binding.tvMealName.text = meal.strMeal
        }
    }

    // TODO: i need to use this class diffUtils because it is that make performance for my recycler veiw
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val meals = differ.currentList[position]
        holder.bind(meals)
    }
}