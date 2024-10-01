package com.example.shoppinggroceryapp.views.sharedviews.profileviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity
import com.example.shoppinggroceryapp.model.entities.user.UserEntity

class EditProfileViewModel(var userDao: UserDao):ViewModel() {

    var recentlyBoughtList:MutableLiveData<MutableList<ProductEntity>> = MutableLiveData()
    var userEntity:MutableLiveData<UserEntity> = MutableLiveData()
    fun saveDetails(oldEmail:String,firstName:String,lastName:String,email:String,phone: String,image:String){
        Thread {
            val user = userDao.getUserData(oldEmail)
            val userEntityTmp = UserEntity(
                userId = user.userId,
                userImage = image,
                userFirstName = firstName,
                userLastName = lastName,
                userEmail = email,
                userPhone = phone,
                userPassword = user.userPassword,
                dateOfBirth = user.dateOfBirth,
                isRetailer = user.isRetailer)
            userDao.updateUser(userEntityTmp)
        }.start()
    }

    fun saveUserImage(oldEmail:String,mainImage:String){
        Thread {
            val user = userDao.getUserData(oldEmail)
            userDao.updateUser(UserEntity(
                userId = user.userId,
                userImage = mainImage,
                userFirstName = user.userFirstName,
                userLastName = user.userLastName,
                userEmail = user.userEmail,
                userPhone = user.userPhone,
                userPassword = user.userPassword,
                dateOfBirth = user.dateOfBirth,
                isRetailer = user.isRetailer
            ))
        }.start()
    }

    fun getPurchasedProducts(userId:Int){
        Thread{
            val list = mutableListOf<ProductEntity>()
            for(i in userDao.getBoughtProductsList(userId)){
                for(j in userDao.getProductsByCartId(i.cartId)){
                    if(j !in list){
                        list.add(j)
                    }
                }
            }
            list.reverse()
            recentlyBoughtList.postValue(list)
        }.start()
    }

    fun getUser(emailOrPhone:String){
        Thread{
            userEntity.postValue(userDao.getUserData(emailOrPhone))
        }.start()
    }

    fun savePassword(userEntity:UserEntity){
        Thread {
            userDao.updateUser(userEntity)
        }.start()
    }
}