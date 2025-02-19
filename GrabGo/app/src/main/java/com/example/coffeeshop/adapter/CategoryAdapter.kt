package com.example.coffeeshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.R
import com.example.coffeeshop.databinding.ViewholderCategoryBinding
import com.example.coffeeshop.model.CategoryModel

class CategoryAdapter(
    val items: MutableList<CategoryModel>,
    val listener: (String) ->Unit)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    //private lateinit var context: Context
    //private var selectedPosition = -1
    //private var lastSelectedPosition = -1
    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleCat.text = item.title

        holder.binding.titleCat.setBackgroundResource(
            if (selectedPosition == position) R.drawable.orange_bg else R.drawable.edittext_bg
        )
        holder.binding.root.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            // Navigate to the selected page
            listener.invoke(item.title)
        }

    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}