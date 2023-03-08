package com.example.easyfood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.adapters.CategoryAdapter
import com.example.easyfood.databinding.FragmentCategoriesBinding
import com.example.easyfood.pojo.Category
import com.example.easyfood.ui.activites.MainActivity
import com.example.easyfood.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var categoriesAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.observeCategoryItemsLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategories(categories as ArrayList<Category>)
        })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoryAdapter()
        binding.rvCategories.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }
}