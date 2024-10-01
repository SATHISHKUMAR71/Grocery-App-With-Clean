package com.example.shoppinggroceryapp.views.userviews.cartview.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity
import com.example.shoppinggroceryapp.model.entities.user.AddressEntity

class CartViewModel(var userDao: UserDao):ViewModel() {

    var cartProducts:MutableLiveData<List<ProductEntity>> = MutableLiveData()
    var totalPrice:MutableLiveData<Float> = MutableLiveData()
    var addressEntityList:MutableLiveData<List<AddressEntity>> = MutableLiveData()
    fun getProductsByCartId(cartId:Int){
        Thread{
            cartProducts.postValue(userDao.getProductsByCartId(cartId))
        }.start()
    }


    fun calculateInitialPrice(cartId: Int){
        Thread{
            val list = userDao.getCartItems(cartId)
            var price = 49f
            for(i in list){
                price += (i.unitPrice*i.totalItems)
            }
            totalPrice.postValue(price)
        }.start()
    }

    fun getAddressListForUser(userId:Int){
        Thread{
            addressEntityList.postValue(userDao.getAddressListForUser(userId))
        }.start()
    }
}