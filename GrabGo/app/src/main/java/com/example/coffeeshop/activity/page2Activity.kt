package com.example.coffeeshop.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeeshop.adapter.PopularAdapter
import com.example.coffeeshop.databinding.ActivityPageBinding
import com.example.coffeeshop.viewmodel.MainViewModel

class page2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPageBinding
    private val viewModel: MainViewModel by viewModels() // ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPageBinding.inflate(layoutInflater)
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
        binding.progressBarPopular.visibility = View.VISIBLE

        viewModel.popular.observe(this, Observer { itemList ->
            binding.recyclerViewPopular.layoutManager = GridLayoutManager(this, 2) // 2 columns
            binding.recyclerViewPopular.adapter = PopularAdapter(itemList)
            binding.progressBarPopular.visibility = View.GONE
        })

        viewModel.loadPopular() // Load data
    }
}