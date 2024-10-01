package com.example.shoppinggroceryapp.views.retailerviews.addeditproduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListFragment
import com.example.shoppinggroceryapp.model.dao.ProductDao
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.entities.products.BrandDataEntity
import com.example.shoppinggroceryapp.model.entities.products.CategoryEntity
import com.example.shoppinggroceryapp.model.entities.products.ImagesEntity
import com.example.shoppinggroceryapp.model.entities.products.ParentCategoryEntity
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productdetail.ProductDetailViewModel

class AddEditProductViewModel(var retailerDao: RetailerDao, var productDao: ProductDao):ViewModel() {

    var brandName:MutableLiveData<String> = MutableLiveData()
    var parentArray:MutableLiveData<Array<String>> = MutableLiveData()
    var imageList:MutableLiveData<List<ImagesEntity>> = MutableLiveData()
    var parentCategory:MutableLiveData<String> = MutableLiveData()
    var childArray:MutableLiveData<Array<String>> = MutableLiveData()
    var categoryImage:MutableLiveData<String> = MutableLiveData()
    companion object {
        var modifiedProductEntity: MutableLiveData<ProductEntity> = MutableLiveData()
    }

    fun getBrandName(brandId:Long){
        Thread{
            synchronized(ProductDetailViewModel.brandLock) {
                brandName.postValue(retailerDao.getBrandName(brandId))
            }
        }.start()
    }

    fun getParentArray(){
        Thread{
            parentArray.postValue(productDao.getParentCategoryName())
        }.start()
    }

    fun getParentCategory(childName:String){
        Thread{
            parentCategory.postValue(productDao.getParentCategoryName(childName))
        }.start()
    }

    fun getChildArray(){
        Thread {
            childArray.postValue(productDao.getChildCategoryName())
        }.start()
    }

    fun getParentCategoryImage(parentCategoryName:String){
        Thread{
            categoryImage.postValue(retailerDao.getParentCategoryImage(parentCategoryName))
        }.start()
    }

    fun getParentCategoryImageForParent(parentCategoryName:String){
        Thread{
            categoryImage.postValue(retailerDao.getParentCategoryImageForParent(parentCategoryName))
        }.start()
    }

    fun getChildArray(parentName:String){
        Thread {
            childArray.postValue(productDao.getChildCategoryName(parentName))
        }.start()
    }

    fun addParentCategory(parentCategoryEntity: ParentCategoryEntity){
        Thread{
            retailerDao.addParentCategory(parentCategoryEntity)
        }.start()
    }

    fun addSubCategory(categoryEntity: CategoryEntity){
        Thread{
            retailerDao.addSubCategory(categoryEntity)
        }.start()
    }

    fun getImagesForProduct(productId: Long){
        Thread{
            imageList.postValue(retailerDao.getImagesForProduct(productId))
        }.start()
    }

    fun updateInventory(brandName:String, isNewProduct:Boolean, productEntity: ProductEntity, productId:Long?, imageList: List<String>, deletedImageList:MutableList<String>){
        var brand:BrandDataEntity
        Thread{
            synchronized(ProductDetailViewModel.brandLock) {
                brand = retailerDao.getBrandWithName(brandName)
                var prod:ProductEntity
                var lastProductEntity:ProductEntity
                if (brand == null) {
                    retailerDao.addNewBrand(BrandDataEntity(0, brandName))
                    brand = retailerDao.getBrandWithName(brandName)
                }
                if (isNewProduct) {
                    prod = productEntity.copy(brandId = brand.brandId)
                    modifiedProductEntity.postValue(prod)
                    retailerDao.addProduct(prod)
                    lastProductEntity = retailerDao.getLastProduct()
                } else {
                    prod = productEntity.copy(brandId = brand.brandId, productId = productId!!)
                    retailerDao.updateProduct(prod)
                    modifiedProductEntity.postValue(prod)
                    lastProductEntity = prod
                }

//                for(j in retailerDao.getImagesForProduct(lastProduct.productId)){
//                    retailerDao.deleteImage(j)
//                }
                for(j in deletedImageList){
                    retailerDao.deleteImage(retailerDao.getSpecificImage(j))
                }
                for(i in imageList){
                    retailerDao.addImagesInDb(ImagesEntity(0,lastProductEntity.productId,i))
                }
                ProductListFragment.selectedProductEntity.postValue(prod)
            }

        }.start()
    }



}