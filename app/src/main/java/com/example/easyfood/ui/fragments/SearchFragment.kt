package com.example.easyfood.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.adapters.MealsAdapter
import com.example.easyfood.databinding.FragmentSearchBinding
import com.example.easyfood.ui.activites.MainActivity
import com.example.easyfood.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        prepareRecyclerView()

        binding.imgSearchArrow.setOnClickListener {
            searchMeals()
        }

        observeSearchMealLiveData()

        // TODO: i need to make search when the user write
        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {

                // TODO: i need to use delay because i am search from api to don't crashing
                delay(500)
                viewModel.searchMeals(searchQuery.toString())
            }
        }

    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchLiveData().observe(viewLifecycleOwner, Observer { mealsList ->
            searchAdapter.differ.submitList(mealsList)
        })
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchAdapter = MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }
}