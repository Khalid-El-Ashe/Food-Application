package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.PopularItemsBinding
import com.example.easyfood.pojo.MealsByCategory

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.MostPopularViewHolder>() {

    // TODO: i need to make clicked the items
    lateinit var onItemClick: ((MealsByCategory) -> Unit)
    private var mealsList = ArrayList<MealsByCategory>()

    // TODO: i need to show bottom sheet when you long click
    var onLongClick: ((MealsByCategory) -> Unit)? = null

    fun setMeals(mealsList: ArrayList<MealsByCategory>) {
        this.mealsList = mealsList
        notifyDataSetChanged() // TODO: this is to refresh the adapter and update the views
    }

    inner class MostPopularViewHolder(val binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: MealsByCategory) {
            Glide.with(itemView).load(meal.strMealThumb).into(binding.imgPopularMealItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        return MostPopularViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
//        Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
//            .into(holder.binding.imgPopularMealItem)
        val meal = mealsList[position]
        holder.bind(meal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }

        holder.itemView.setOnLongClickListener {
            onLongClick?.invoke(meal)
            true
        }
    }
}