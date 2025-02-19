package com.example.coffeeshop.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshop.adapter.CategoryAdapter
import com.example.coffeeshop.adapter.OffersAdapter
import com.example.coffeeshop.adapter.PopularAdapter
import com.example.coffeeshop.databinding.ActivityMainBinding
import com.example.coffeeshop.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity() {

    private val viewModel = MainViewModel()
    //private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth // Firebase Auth instance

    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bottomNavigation.background = null

        mAuth = FirebaseAuth.getInstance() // Initialize Firebase Auth

        // Logout Button Click Listener
        binding.button8.setOnClickListener {
            logoutUser()
        }
        initCategory()
        initPopular()
        initOffer()
        bottomMenu()
    }


    private fun bottomMenu() {
        binding.cartBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logoutUser() {
        mAuth.signOut() // Sign out the current user
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish() // Close MainActivity so user can't go back without logging in
        Toast.makeText(this@MainActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            // Redirect to LoginActivity if user is not logged in
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initOffer() {
        binding.progressBarOffer.visibility = View.VISIBLE
        viewModel.offer.observe(this, Observer {
            binding.recyclerViewOffer.layoutManager =
                LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.recyclerViewOffer.adapter = OffersAdapter(it)
            binding.progressBarOffer.visibility = View.GONE
        })
        viewModel.loadOffer()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.recyclerViewPopular.layoutManager =
                    GridLayoutManager(this@MainActivity, 2) // 2 columns

            binding.recyclerViewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE

        // Set up LayoutManager once
        binding.recyclerViewCategory.layoutManager = LinearLayoutManager(
            this@MainActivity, LinearLayoutManager.HORIZONTAL, false
        )

        viewModel.category.observe(this, Observer { categoryList ->
            binding.recyclerViewCategory.adapter = CategoryAdapter(categoryList) { pageTag ->
                navigateToPage(pageTag) // Ensure this function exists
            }
            binding.progressBarCategory.visibility = View.GONE
        })
        viewModel.loadCategory()
    }
    private fun navigateToPage(pageTag: String) {
        when (pageTag) {
            "All" -> startActivity(Intent(this, ActivityPage::class.java))
            "Drinks" -> startActivity(Intent(this, AllDrinksActivity::class.java))
            "Salads" -> startActivity(Intent(this, SaladActivity::class.java))
            else -> Toast.makeText(this, "Page not found", Toast.LENGTH_SHORT).show()
        }
    }

}