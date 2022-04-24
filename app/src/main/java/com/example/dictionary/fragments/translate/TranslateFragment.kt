package com.example.dictionary.fragments.translate

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dictionary.databinding.FragmentTranslateBinding
import com.example.dictionary.fragments.main.MainFragmentDirections
import com.example.dictionary.fragments.words.WordsViewModel

class TranslateFragment : Fragment() {
    private lateinit var binding: FragmentTranslateBinding
    private val viewModel: TranslateViewModel by activityViewModels()
    private val args: TranslateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTranslateBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.translate(args.query)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rawText.text = args.query

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.translate.observe(viewLifecycleOwner) { translate ->
            binding.translateText.text = translate
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

            toTranslateButton.setOnClickListener {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                }
                getVoiceInput.launch(intent)
            }
        }
    }

    private val getVoiceInput = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val chop = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.first()
        chop?.let {
            viewModel.translate(it)
        }
    }
}