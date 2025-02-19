package com.example.coffeeshop.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeeshop.adapter.PopularAdapter
import com.example.coffeeshop.databinding.ActivityAllProductsBinding
import com.example.coffeeshop.databinding.ActivityPageBinding
import com.example.coffeeshop.viewmodel.MainViewModel

class ActivityPage : AppCompatActivity() {

    private lateinit var binding: ActivityAllProductsBinding
    private val viewModel: MainViewModel by viewModels() // ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the category name passed from MainActivity
        val categoryName = intent.getStringExtra("CATEGORY_NAME")

        // Display category name or use it for further actions
        if (categoryName != null) {
            Toast.makeText(this, "Category: $categoryName", Toast.LENGTH_SHORT).show()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.progressBar7.visibility = View.VISIBLE
        viewModel.all.observe(this, Observer { itemList ->
            binding.recyclerViewitems.layoutManager = GridLayoutManager(this, 2) // 2 columns
            binding.recyclerViewitems.adapter = PopularAdapter(itemList)
            binding.progressBar7.visibility = View.GONE
        })

        viewModel.loadall() // Load data
    }
}