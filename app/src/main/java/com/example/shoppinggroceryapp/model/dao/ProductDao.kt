package com.example.shoppinggroceryapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinggroceryapp.model.dataclass.ChildCategoryName
import com.example.shoppinggroceryapp.model.entities.products.CategoryEntity
import com.example.shoppinggroceryapp.model.entities.products.ParentCategoryEntity

@Dao
interface ProductDao:UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addParentCategory(parentCategoryEntity: ParentCategoryEntity)

    @Query("SELECT CategoryEntity.parentCategoryName FROM CategoryEntity WHERE CategoryEntity.categoryName=:childName")
    fun getParentCategoryName(childName:String):String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSubCategory(categoryEntity: CategoryEntity)

    @Query("Select * from ParentCategoryEntity")
    fun getAllParentCategory():List<ParentCategoryEntity>

    @Query("SELECT * FROM CATEGORY")
    fun getAllCategory():List<CategoryEntity>

    @Query("SELECT * FROM ParentCategoryEntity")
    fun getParentCategoryList():List<ParentCategoryEntity>


    @Query("SELECT CategoryEntity.categoryName FROM CategoryEntity Where CategoryEntity.parentCategoryName=:parent")
    fun getChildName(parent:String):List<ChildCategoryName>

    @Query("SELECT ParentCategoryEntity.parentCategoryName FROM ParentCategoryEntity")
    fun getParentCategoryName():Array<String>

    @Query("SELECT CategoryEntity.categoryName FROM CategoryEntity")
    fun getChildCategoryName():Array<String>

    @Query("SELECT CategoryEntity.categoryName FROM CategoryEntity Where CategoryEntity.parentCategoryName=:parentName")
    fun getChildCategoryName(parentName:String):Array<String>

    @Query("SELECT * FROM CategoryEntity WHERE CategoryEntity.parentCategoryName=:parentCategoryName")
    fun getChildCategoryList(parentCategoryName:String):List<CategoryEntity>

    @Query("SELECT * FROM ParentCategoryEntity JOIN CategoryEntity ON CategoryEntity.parentCategoryName=ParentCategoryEntity.parentCategoryName")
    fun getAllCategoryAndParentCategory():Map<ParentCategoryEntity,List<CategoryEntity>>

}