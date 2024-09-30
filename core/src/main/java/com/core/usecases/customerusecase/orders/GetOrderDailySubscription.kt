package com.core.usecases.customerusecase.orders

import com.core.data.repository.CustomerRepository

class GetOrderDailySubscription(private val customerRepository: CustomerRepository) {
    fun invoke(userId:Int){
        customerRepository.getOrdersForUserDailySubscription(userId)
    }
}