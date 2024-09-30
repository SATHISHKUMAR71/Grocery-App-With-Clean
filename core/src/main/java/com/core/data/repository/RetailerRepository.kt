package com.core.data.repository

import com.core.data.datasource.customerdatasource.CustomerDataSource
import com.core.data.datasource.retailerdatasource.ProductRetailerDataSource
import com.core.domain.products.BrandData
import com.core.domain.products.Category
import com.core.domain.products.DeletedProductList
import com.core.domain.products.Images
import com.core.domain.products.ParentCategory
import com.core.domain.products.Product
import com.core.domain.recentlyvieweditems.RecentlyViewedItems

class RetailerRepository(private var productRetailerDataSource: ProductRetailerDataSource){

    fun addProduct(product: Product){
        productRetailerDataSource.addProduct(product)
    }

    fun addParentCategory(parentCategory: ParentCategory){
        productRetailerDataSource.addParentCategory(parentCategory)
    }

    fun addSubCategory(category: Category){
        productRetailerDataSource.addSubCategory(category)
    }

    fun addNewBrand(brandData: BrandData){
        productRetailerDataSource.addNewBrand(brandData)
    }

    fun getLastProduct():Product{
        return productRetailerDataSource.getLastProduct()
    }
    fun updateProduct(product: Product){
        productRetailerDataSource.updateProduct(product)
    }
    fun addProductInRecentlyViewedItems(recentlyViewedItem: RecentlyViewedItems){
        productRetailerDataSource.addProductInRecentlyViewedItems(recentlyViewedItem)
    }

    fun getProductsInRecentList(productId:Long,user:Int):RecentlyViewedItems{
        return productRetailerDataSource.getProductsInRecentList(productId,user)
    }
    fun getImagesForProduct(productId: Long):List<Images>{
        return productRetailerDataSource.getImagesForProduct(productId)
    }
    fun getSpecificImage(image:String):Images{
        return productRetailerDataSource.getSpecificImage(image)
    }
    fun addDeletedProduct(deletedProductList: DeletedProductList){
        return productRetailerDataSource.addDeletedProduct(deletedProductList)
    }
    fun deleteProduct(product: Product){
        productRetailerDataSource.deleteProduct(product)
    }

    fun getBrandWithName(brandName:String):BrandData{
        return productRetailerDataSource.getBrandWithName(brandName)
    }

    fun getParentCategoryImageForParent(childCategoryName: String):String{
        return productRetailerDataSource.getParentCategoryImageForParent(childCategoryName)
    }
    fun getParentCategoryImage(parentCategoryName: String):String{
        return productRetailerDataSource.getParentCategoryImage(parentCategoryName)
    }
    fun addProductImagesInDb(images: Images){
        productRetailerDataSource.addProductImagesInDb(images)
    }

    fun deleteProductImage(images: Images){
        productRetailerDataSource.deleteProductImage(images)
    }
}