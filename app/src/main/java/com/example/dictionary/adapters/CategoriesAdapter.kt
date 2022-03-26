package com.example.dictionary.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ViewCategoryBinding
import com.example.dictionary.model.entities.Category

class CategoriesAdapter(
    private val onClick: (Category) -> Unit = {}
) : ListAdapter<Category, CategoriesAdapter.ViewHolder>(DetailImageDiffUtilCallback()) {

    inner class ViewHolder(private val binding: ViewCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.categoryTitle.text = category.name
            if (category.image != null) {
                val bmp = BitmapFactory.decodeByteArray(category.image, 0, category.image.count())
                binding.categoryImage.setImageBitmap(bmp)
            }
            binding.root.setOnClickListener { onClick(category) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return getViewHolder(parent)
    }

    private fun getViewHolder(parent: ViewGroup): ViewHolder {
        val binding = ViewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class DetailImageDiffUtilCallback : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }
}