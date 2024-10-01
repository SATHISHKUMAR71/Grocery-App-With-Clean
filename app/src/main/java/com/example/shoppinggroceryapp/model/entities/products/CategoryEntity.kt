package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class CategoryEntity(
    @PrimaryKey
    val categoryName:String,
    val parentCategoryName:String,
    val categoryDescription:String
)