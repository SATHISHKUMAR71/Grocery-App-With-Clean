package com.example.shoppinggroceryapp.views.sharedviews.filter

import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity

class FilterViewModel(var userDao: UserDao):ViewModel() {



    fun filterList(productEntityList:List<ProductEntity>, offer:Float):List<ProductEntity>{
        return productEntityList.filter { it.offer>=offer }
    }

    fun filterListBelow(productEntityList:List<ProductEntity>, offer:Float):List<ProductEntity>{
        return productEntityList.filter { it.offer<offer }
    }

}