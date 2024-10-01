package com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.user.UserEntity

class SignUpViewModel(var userDao: UserDao):ViewModel() {
    var registrationStatus:MutableLiveData<Boolean> = MutableLiveData()
    var registrationStatusInt:MutableLiveData<Int> = MutableLiveData()

    fun registerNewUser(userEntity: UserEntity){
        Thread{
            val email = userDao.getUserData(userEntity.userEmail)
            val phone = userDao.getUserData(userEntity.userPhone)
            if(phone==null&&email==null) {
                Thread {
                    var id = userDao.addUser(userEntity)
                    var userEmail = userEntity.userEmail
                    var userPhone = userEntity.userPhone
                    registrationStatusInt.postValue(0)
                    registrationStatus.postValue(true)
                }.start()
            }
            else if(phone!=null && email!=null){
                registrationStatusInt.postValue(1)
//                registrationStatus.postValue(false)
            }
            else if(phone!=null){
                registrationStatusInt.postValue(2)
            }
            else{
                registrationStatusInt.postValue(3)
            }
        }.start()
    }
}