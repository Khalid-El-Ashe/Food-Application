package com.example.easyfood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfood.adapters.MealsAdapter
import com.example.easyfood.databinding.FragmentFavoritesBinding
import com.example.easyfood.ui.activites.MainActivity
import com.example.easyfood.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: i need to make instance of view model class from main class
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFav()

        // TODO: i need to make swipe to delete item
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favAdapter.differ.currentList[position])
                Snackbar.make(view, "success deleted item", Snackbar.LENGTH_LONG).show()

//                Snackbar.make(
//                    view,
//                    "Are you need to delete this item?",
//                    Snackbar.LENGTH_LONG
//                ).setAction("Undo", View.OnClickListener {
//                    viewModel.insertMeal(favAdapter.differ.currentList[position])
//                }).show()

            }
        }

        // TODO: in here i need to enable my touch helper
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFav)
    }

    private fun prepareRecyclerView() {

        favAdapter = MealsAdapter()
        binding.rvFav.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }

    }

    private fun observeFav() {
        viewModel.observeFavMealLiveData().observe(viewLifecycleOwner, Observer { meals ->
            favAdapter.differ.submitList(meals)

            if (meals.isNullOrEmpty()) {
                binding.tvNoFav.visibility = View.VISIBLE
            } else {
                binding.tvNoFav.visibility = View.GONE
            }
        })
    }
}