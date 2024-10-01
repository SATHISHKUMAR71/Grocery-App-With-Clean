package com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.order.CartMappingEntity
import com.example.shoppinggroceryapp.model.entities.user.UserEntity

class LoginViewModel(var userDao: UserDao) :ViewModel(){
    var userEntity:MutableLiveData<UserEntity> = MutableLiveData()
    var userEntityName:MutableLiveData<UserEntity> = MutableLiveData()

    fun isUser(userData:String){
        Thread{
            userEntityName.postValue(userDao.getUserData(userData))
        }.start()
    }

    fun validateUser(email:String,password:String){
        Thread {
            userEntity.postValue(userDao.getUser(email, password))
        }.start()
    }

    fun assignCartForUser(){
        Thread{
            val cart:CartMappingEntity? = userDao.getCartForUser(userEntity.value?.userId?:-1)
            if(cart==null){
               userDao.addCartForUser(CartMappingEntity(0,userEntity.value?.userId?:-1,"available"))
               val newCart:CartMappingEntity? =  userDao.getCartForUser(userEntity.value?.userId?:-1)
                while (newCart==null) {
                }
                MainActivity.cartId = newCart.cartId
            }
            else{
                MainActivity.cartId = cart.cartId
            }
        }.start()
    }
}