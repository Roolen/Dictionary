package com.example.dictionary.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.adapters.CategoriesAdapter
import com.example.dictionary.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadCategories()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setListeners()
        setAdapters()
    }

    private fun setObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.submitList(categories)
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

            toFavouriteButton.setOnClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionMainToFavourites()
                )
            }
        }
    }

    private val categoriesAdapter by lazy {
        CategoriesAdapter(onClick = {
            findNavController().navigate(
                MainFragmentDirections.actionMainToWords(it.id, it.name)
            )
        })
    }

    private fun setAdapters() {
        binding.categoryRecycler.adapter = categoriesAdapter
        binding.categoryRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

}