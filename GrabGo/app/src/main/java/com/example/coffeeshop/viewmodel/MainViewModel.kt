package com.example.coffeeshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeeshop.model.CategoryModel
import com.example.coffeeshop.model.ItemsModel
import com.google.firebase.database.*

class MainViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _popular = MutableLiveData<MutableList<ItemsModel>>()
    private val _offer = MutableLiveData<MutableList<ItemsModel>>()
    private val _allDrinks = MutableLiveData<MutableList<ItemsModel>>()  // Added drinks LiveData
    private val _allSalad = MutableLiveData<MutableList<ItemsModel>>()
    private val _all = MutableLiveData<MutableList<ItemsModel>>()

    val category: LiveData<MutableList<CategoryModel>> = _category
    val popular: LiveData<MutableList<ItemsModel>> = _popular
    val offer: LiveData<MutableList<ItemsModel>> = _offer
    val allDrinks: LiveData<MutableList<ItemsModel>> = _allDrinks  // Expose drinks LiveData
    val allSalad: LiveData<MutableList<ItemsModel>> = _allSalad
    val all: LiveData<MutableList<ItemsModel>> = _all

    fun loadCategory() {
        val ref = firebaseDatabase.getReference("Category")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(CategoryModel::class.java)?.let { lists.add(it) }
                }
                _category.postValue(lists)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun loadPopular() {
        val ref = firebaseDatabase.getReference("Items")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(ItemsModel::class.java)?.let { lists.add(it) }
                }
                _popular.postValue(lists)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun loadOffer() {
        val ref = firebaseDatabase.getReference("Offers")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(ItemsModel::class.java)?.let { lists.add(it) }
                }
                _offer.postValue(lists)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun loadAllDrinks() {
        val ref = firebaseDatabase.getReference("Drinks")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(ItemsModel::class.java)?.let { lists.add(it) }
                }
                _allDrinks.postValue(lists)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun loadSalad() {
        val ref = firebaseDatabase.getReference("Salad")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(ItemsModel::class.java)?.let { lists.add(it) }
                }
                _allSalad.postValue(lists)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun loadall() {
        val ref = firebaseDatabase.getReference("totel")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(ItemsModel::class.java)?.let { lists.add(it) }
                }
                _all.postValue(lists)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}