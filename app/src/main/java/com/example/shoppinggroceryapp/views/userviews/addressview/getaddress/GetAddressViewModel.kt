package com.example.shoppinggroceryapp.views.userviews.addressview.getaddress

import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.user.Address

class GetAddressViewModel(var userDao: UserDao):ViewModel() {
    fun addAddress(address:Address){
        Thread{
            userDao.addAddress(address)
        }.start()
    }

    fun updateAddress(address: Address){
        Thread{
            userDao.updateAddress(address)

        }.start()
    }
}