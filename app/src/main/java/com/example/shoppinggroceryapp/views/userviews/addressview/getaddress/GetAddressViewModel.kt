package com.example.shoppinggroceryapp.views.userviews.addressview.getaddress

import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.user.AddressEntity

class GetAddressViewModel(var userDao: UserDao):ViewModel() {
    fun addAddress(addressEntity:AddressEntity){
        Thread{
            userDao.addAddress(addressEntity)
        }.start()
    }

    fun updateAddress(addressEntity: AddressEntity){
        Thread{
            userDao.updateAddress(addressEntity)

        }.start()
    }
}