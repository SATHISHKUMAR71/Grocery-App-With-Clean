package com.example.shoppinggroceryapp.views.userviews.addressview.savedaddress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.user.AddressEntity

class SavedAddressViewModel(var userDao: UserDao):ViewModel() {

    var addressEntityList:MutableLiveData<List<AddressEntity>> = MutableLiveData()
    fun getAddressListForUser(userId:Int){
        Thread{
            addressEntityList.postValue(userDao.getAddressListForUser(userId))
        }.start()
    }
}