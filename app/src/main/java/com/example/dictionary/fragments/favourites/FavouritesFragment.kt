package com.example.dictionary.fragments.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.LinearItemDecorations
import com.example.dictionary.R
import com.example.dictionary.adapters.WordsAdapter
import com.example.dictionary.databinding.FragmentFavouritesBinding
import com.example.dictionary.gone
import com.example.dictionary.visible

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private val viewModel: FavouritesViewModel by activityViewModels()
    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavouritesBinding.inflate(inflater, container, false).apply {
        binding = this

        wordsRecycler.addItemDecoration(
            LinearItemDecorations(
                sideMarginsDimension = R.dimen.normalMargin,
                marginBetweenElementsDimension = R.dimen.normalMargin
            )
        )
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadFavorites()

        setObservers()
        setListeners()
        setAdapters()
    }

    private fun setObservers() {
        viewModel.favourites.observe(viewLifecycleOwner) { words ->
            if (words.count() > 0) {
                wordsAdapter.submitList(words)
                binding.errorMessage.gone()
                binding.wordsRecycler.visible()
            }
            else {
                binding.errorMessage.visible()
                binding.wordsRecycler.gone()
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private val wordsAdapter by lazy {
        WordsAdapter(
            onHeartClick = { word ->
                viewModel.changeFavorite(word)
            }
        )
    }

    private fun setAdapters() {
        binding.wordsRecycler.adapter = wordsAdapter
        binding.wordsRecycler.layoutManager = LinearLayoutManager(requireContext())
    }
}