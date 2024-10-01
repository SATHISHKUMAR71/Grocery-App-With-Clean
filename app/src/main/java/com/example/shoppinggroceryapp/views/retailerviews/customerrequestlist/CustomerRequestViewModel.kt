package com.example.shoppinggroceryapp.views.retailerviews.customerrequestlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.dataclass.CustomerRequestWithName
import com.example.shoppinggroceryapp.model.entities.order.OrderDetailsEntity
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductDataEntity

class CustomerRequestViewModel(var userDao: UserDao):ViewModel() {

    var customerRequestList:MutableLiveData<List<CustomerRequestWithName>> = MutableLiveData()
    var selectedOrderLiveData:MutableLiveData<OrderDetailsEntity> = MutableLiveData()
    var correspondingCartLiveData:MutableLiveData<List<CartWithProductDataEntity>> = MutableLiveData()

    fun getCustomerRequest(){
        Thread {
            customerRequestList.postValue(userDao.getDataFromCustomerReqWithName())
        }.start()
    }

    fun getOrderDetails(orderId:Int){
        Thread {
            selectedOrderLiveData.postValue(userDao.getOrderDetails(orderId))
        }.start()
    }

    fun getCorrespondingCart(cartId:Int){
        Thread {
                correspondingCartLiveData.postValue(userDao.getProductsWithCartId(cartId))
        }.start()
    }
}