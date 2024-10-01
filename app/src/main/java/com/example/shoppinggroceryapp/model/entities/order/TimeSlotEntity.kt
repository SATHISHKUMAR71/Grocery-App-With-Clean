package com.example.shoppinggroceryapp.model.entities.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeSlotEntity(
    @PrimaryKey(autoGenerate = false)
    val orderId:Int,
    val timeId:Int
)