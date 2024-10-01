package com.example.shoppinggroceryapp.model.entities.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailySubscriptionEntity(
    @PrimaryKey(autoGenerate = false)
    val orderId:Int
)