package com.example.shoppinggroceryapp.views.sharedviews.productviews.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.entities.order.CartEntity
import com.example.shoppinggroceryapp.model.entities.products.DeletedProductListEntity
import com.example.shoppinggroceryapp.model.entities.products.ImagesEntity
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity
import com.example.shoppinggroceryapp.model.entities.recentlyvieweditems.RecentlyViewedItemsEntity

class ProductDetailViewModel(var retailerDao: RetailerDao):ViewModel() {


    var cartProducts:MutableLiveData<List<ProductEntity>> = MutableLiveData()
    var brandName:MutableLiveData<String> = MutableLiveData()
    var isCartEntityAvailable:MutableLiveData<CartEntity> =MutableLiveData()
    var similarProductsLiveData:MutableLiveData<List<ProductEntity>> = MutableLiveData()
    var imageList:MutableLiveData<List<ImagesEntity>> = MutableLiveData()
    var lock = Any()
    companion object{
        var brandLock = Any()
    }
    fun getBrandName(brandId:Long){
        Thread {
            synchronized(brandLock){
                brandName.postValue(retailerDao.getBrandName(brandId))
            }
        }.start()
    }

    fun getProductsByCartId(cartId:Int){
        Thread{
            cartProducts.postValue(retailerDao.getProductsByCartId(cartId))
        }.start()
    }

    fun addInRecentlyViewedItems(productId: Long){
        Thread {
            if(retailerDao.getProductsInRecentList(productId,MainActivity.userId.toInt())==null) {
                retailerDao.addProductInRecentlyViewedItems(RecentlyViewedItemsEntity(0, MainActivity.userId.toInt(),productId))
            }
        }.start()
    }

    fun getCartForSpecificProduct(cartId:Int,productId:Int){
        Thread{
            isCartEntityAvailable.postValue(retailerDao.getSpecificCart(cartId,productId))
        }.start()
    }

    fun addProductInCart(cartEntity:CartEntity){
        Thread{
            synchronized(lock){
                retailerDao.addItemsToCart(cartEntity)
            }
        }.start()
    }

    fun updateProductInCart(cartEntity:CartEntity){
        Thread{
            synchronized(lock){
                retailerDao.updateCartItems(cartEntity)
            }
        }.start()
    }

    fun removeProductInCart(cartEntity:CartEntity){
        Thread{
            synchronized(lock){
                retailerDao.removeProductInCart(cartEntity)
            }
        }.start()
    }

    fun getSimilarProduct(category:String){
        Thread{
            similarProductsLiveData.postValue(retailerDao.getProductByCategory(category))
        }.start()
    }

    fun getImagesForProducts(productId: Long){
        Thread{
            imageList.postValue(retailerDao.getImagesForProduct(productId))
        }.start()
    }
    fun removeProduct(productEntity: ProductEntity){
        Thread {
            retailerDao.addDeletedProduct(DeletedProductListEntity(productId = productEntity.productId, brandId = productEntity.brandId,
                categoryName = productEntity.categoryName, productName = productEntity.productName, productDescription = productEntity.productDescription,
                price = productEntity.price, offer = productEntity.offer, productQuantity = productEntity.productQuantity, mainImage = productEntity.mainImage, isVeg = productEntity.isVeg,
                manufactureDate = productEntity.manufactureDate, expiryDate = productEntity.expiryDate, availableItems = productEntity.availableItems))
            retailerDao.deleteProduct(productEntity)
        }.start()
    }

}