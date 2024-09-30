package com.core.data.datasource.retailerdatasource

import com.core.domain.order.DailySubscription
import com.core.domain.order.MonthlyOnce
import com.core.domain.order.TimeSlot
import com.core.domain.order.WeeklyOnce
import com.core.domain.products.BrandData
import com.core.domain.products.Category
import com.core.domain.products.DeletedProductList
import com.core.domain.products.Images
import com.core.domain.products.ParentCategory
import com.core.domain.products.Product
import com.core.domain.recentlyvieweditems.RecentlyViewedItems

interface ProductRetailerDataSource {
    fun addProduct(product: Product)
    fun addParentCategory(parentCategory: ParentCategory)
    fun addSubCategory(category: Category)
    fun addNewBrand(brandData: BrandData)
    fun getLastProduct():Product
    fun updateProduct(product: Product)
    fun addProductInRecentlyViewedItems(recentlyViewedItem: RecentlyViewedItems)
    fun getProductsInRecentList(productId:Long,user:Int):RecentlyViewedItems
    fun getImagesForProduct(productId: Long):List<Images>
    fun getSpecificImage(image:String):Images
    fun addDeletedProduct(deletedProductList: DeletedProductList)
    fun deleteProduct(product: Product)
    fun getBrandWithName(brandName:String):BrandData
    fun getParentCategoryImageForParent(parentCategoryName: String):String
    fun getParentCategoryImage(parentCategoryName: String):String
    fun addProductImagesInDb(images: Images)
    fun deleteProductImage(images: Images)
}
