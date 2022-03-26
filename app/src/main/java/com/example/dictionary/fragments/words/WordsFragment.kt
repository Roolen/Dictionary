package com.example.dictionary.fragments.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.LinearItemDecorations
import com.example.dictionary.R
import com.example.dictionary.adapters.WordsAdapter
import com.example.dictionary.databinding.FragmentWordsBinding

class WordsFragment : Fragment() {
    private lateinit var binding: FragmentWordsBinding
    private val viewModel: WordsViewModel by activityViewModels()
    private val args: WordsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWordsBinding.inflate(inflater, container, false).apply {
        binding = this

        wordsRecycler.addItemDecoration(
            LinearItemDecorations(
                sideMarginsDimension = R.dimen.normalMargin,
                marginBetweenElementsDimension = R.dimen.normalMargin
            )
        )
    }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadWords(args.categoryId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = args.categoryName

        setObservers()
        setListeners()
        setAdapters()
    }

    private fun setObservers() {
        viewModel.words.observe(viewLifecycleOwner) { words ->
            wordsAdapter.submitList(words)
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private val wordsAdapter by lazy {
        WordsAdapter()
    }

    private fun setAdapters() {
        binding.wordsRecycler.adapter = wordsAdapter
        binding.wordsRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

}