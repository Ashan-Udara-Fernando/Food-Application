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
import com.example.coffeeshop.databinding.ActivityDrinksBinding
import com.example.coffeeshop.viewmodel.MainViewModel

class AllDrinksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrinksBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the category name passed from MainActivity
        val categoryName = intent.getStringExtra("CATEGORY_NAME")

        // Display category name or use it for further actions
        if (categoryName != null) {
            Toast.makeText(this, "Category: $categoryName", Toast.LENGTH_SHORT).show()
        }

        initAllDrinks()
    }

    private fun initAllDrinks() {
        binding.progressBar7.visibility = View.VISIBLE

        viewModel.allDrinks.observe(this, Observer { itemList ->
            binding.recyclerViewitems.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerViewitems.adapter = PopularAdapter(itemList)
            binding.progressBar7.visibility = View.GONE
        })

        viewModel.loadAllDrinks()
    }
}