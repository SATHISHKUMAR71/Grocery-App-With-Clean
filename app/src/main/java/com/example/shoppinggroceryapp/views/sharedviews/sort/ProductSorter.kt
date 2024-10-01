package com.example.shoppinggroceryapp.views.sharedviews.sort

import com.example.shoppinggroceryapp.model.entities.products.ProductEntity

class ProductSorter {

    fun sortByDate(productEntityList:List<ProductEntity>):List<ProductEntity>{
        val sortedList =productEntityList.sortedWith(compareBy({it.manufactureDate},{it.productId}))
        return sortedList
    }
    fun sortByExpiryDate(productEntityList:List<ProductEntity>):List<ProductEntity>{
        val sortedList =productEntityList.sortedWith(compareBy({it.expiryDate},{it.productId}))
        return sortedList
    }
    fun sortByDiscount(productEntityList:List<ProductEntity>):List<ProductEntity>{
        val sortedList =productEntityList.sortedWith(compareBy({it.offer},{it.productId})).reversed()
        return sortedList
    }
    fun sortByPriceHighToLow(productEntityList:List<ProductEntity>):List<ProductEntity>{
        val sortedList =productEntityList.sortedWith(compareBy({it.price},{it.productId})).reversed()
        return sortedList
    }

    fun sortByPriceLowToHigh(productEntityList:List<ProductEntity>):List<ProductEntity>{
        val sortedList =productEntityList.sortedWith(compareBy({it.price},{it.productId}))
        return sortedList
    }
}