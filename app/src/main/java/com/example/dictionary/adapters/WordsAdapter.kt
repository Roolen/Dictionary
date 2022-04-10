package com.example.dictionary.adapters

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.databinding.ViewWordBinding
import com.example.dictionary.model.entities.Word
import com.example.dictionary.playMp3
import com.example.dictionary.str

class WordsAdapter(
    private val onClick: (Word) -> Unit = {},
    private val onHeartClick: (Word) -> Unit = {}
) : ListAdapter<Word, WordsAdapter.ViewHolder>(DetailImageDiffUtilCallback()) {
    private val mediaPlayer = MediaPlayer()

    inner class ViewHolder(private val binding: ViewWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word, position: Int) {
            binding.wordName.text = str(R.string.wordName, position + 1, word.value)
            binding.wordTranslate.text = word.translate
            binding.wordTranscription.text = str(R.string.wordTranscription, word.transcription)

            if (word.isFavorite) {
                binding.favouriteButton.setIconResource(R.drawable.ic_heart_fill)
            }
            else {
                binding.favouriteButton.setIconResource(R.drawable.ic_heart)
            }

            binding.soundButton.setOnClickListener {
                word.sound?.let {
                    playMp3(mediaPlayer, it)
                }
            }
            binding.favouriteButton.setOnClickListener { onHeartClick(word) }
            binding.root.setOnClickListener { onClick(word) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return getViewHolder(parent)
    }

    private fun getViewHolder(parent: ViewGroup): ViewHolder {
        val binding = ViewWordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class DetailImageDiffUtilCallback : DiffUtil.ItemCallback<Word>() {

        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean =
            oldItem == newItem
    }
}