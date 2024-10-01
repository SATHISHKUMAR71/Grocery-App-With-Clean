package com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.order.CartEntity
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity

class ProductListViewModel(var userDao: UserDao):ViewModel() {

    var cartEntityList: MutableLiveData<List<CartEntity>> = MutableLiveData()
    var productEntityList: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    var productEntityCategoryList: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    var manufacturedSortedList:MutableLiveData<List<ProductEntity>> = MutableLiveData()
    var cartEntityListForProducts:MutableList<CartEntity?> = mutableListOf()
    fun getCartItems(cartId: Int) {
        Thread {
            cartEntityList.postValue(userDao.getCartItems(cartId))
        }.start()
    }

    fun getOnlyProducts() {
        Thread {
            productEntityList.postValue(userDao.getOnlyProducts())
        }.start()
    }


    fun getProductsByCategory(category: String) {
        Thread {
            var list = userDao.getProductByCategory(category)
            if(list.isEmpty()) {
                list = userDao.getProductsByName(category)
            }
            productEntityCategoryList.postValue(list)
        }.start()
    }

    fun getSpecificCart(cartId: Int,productId:Int,callback: (CartEntity?) -> Unit){
        Thread{
            val cartEntityData:CartEntity? = (userDao.getSpecificCart(cartId,productId))
            callback(cartEntityData)
        }.start()
    }

    fun getBrandName(brandId:Long,callbackBrand: (String?) -> Unit){
        Thread{
            callbackBrand(userDao.getBrandName(brandId))
        }.start()
    }

    fun removeProductInCart(cartEntity: CartEntity){
        Thread{
            userDao.removeProductInCart(cartEntity)
        }.start()
    }

    fun updateItemsInCart(cartEntity: CartEntity){
        Thread{
            userDao.addItemsToCart(cartEntity)
        }.start()
    }

}