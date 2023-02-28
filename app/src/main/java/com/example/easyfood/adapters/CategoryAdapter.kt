package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.CategoryItemBinding
import com.example.easyfood.pojo.Category

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var onItemClick: ((Category) -> Unit)? = null
    private var categoryList = ArrayList<Category>()

    fun setCategories(categoryList: ArrayList<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged() // TODO: this is to refresh the adapter and update the views
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            Glide.with(itemView).load(category.strCategoryThumb).into(binding.imgCategory)
            binding.tvCategoryName.text = category.strCategory
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryes = categoryList[position]
        holder.bind(categoryes)
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryes)
        }
    }
}