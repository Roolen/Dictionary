package com.example.dictionary.fragments.translate

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dictionary.databinding.FragmentTranslateBinding
import com.example.dictionary.playMp3
import com.google.android.material.snackbar.Snackbar

class TranslateFragment : Fragment() {
    private lateinit var binding: FragmentTranslateBinding
    private val viewModel: TranslateViewModel by viewModels()
    private val args: TranslateFragmentArgs by navArgs()

    private val mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTranslateBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.detectLanguage(args.query)
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
        viewModel.codeLanguage.observe(viewLifecycleOwner) { (text, code) ->
            viewModel.translate(text, code)
        }
        viewModel.speech.observe(viewLifecycleOwner) { speech ->
            playMp3(mediaPlayer, speech)
            Snackbar.make(binding.root, "Play speech", 1000).show()
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

            soundButton.setOnClickListener {
                viewModel.speech(binding.translateText.text.toString())
            }
        }
    }

    private val getVoiceInput = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val chop = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.first()
        chop?.let {
            binding.rawText.text = it
            viewModel.detectLanguage(it)
        }
    }
}