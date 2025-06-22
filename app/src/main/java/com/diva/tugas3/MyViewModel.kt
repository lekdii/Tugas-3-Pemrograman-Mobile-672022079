package com.diva.tugas3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: MutableLiveData<List<User>> = _users

    private val _averageproduct = MutableLiveData<Double>()
    val products: MutableLiveData<Double> = _averageproduct

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchUsers(userId: Int){
        viewModelScope.launch {
            try {
                val user = RetrofitInstance.api.getUsers(userId)
                _users.value = listOf(user)
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching product: ${e.message}"
            }
        }
    }

    fun fetchProducts(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProducts()
                val avg = response.products.map { it.price }.average()
                _averageproduct.value = avg
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching product: ${e.message}"
            }
        }
    }
}