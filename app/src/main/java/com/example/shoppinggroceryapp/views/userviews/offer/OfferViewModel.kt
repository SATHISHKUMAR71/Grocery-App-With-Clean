package com.example.shoppinggroceryapp.views.userviews.offer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity

class OfferViewModel(var userDao: UserDao):ViewModel() {
    var offeredProductEntityList:MutableLiveData<List<ProductEntity>> = MutableLiveData()
    fun getOfferedProducts(){
        Thread {
            offeredProductEntityList.postValue(userDao.getOfferedProducts())
        }.start()
    }
}