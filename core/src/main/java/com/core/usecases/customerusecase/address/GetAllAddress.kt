package com.core.usecases.customerusecase.address

import com.core.data.repository.CustomerRepository

class GetAllAddress (private val customerRepository: CustomerRepository){
    fun invoke(userId: Int){
        customerRepository.getAddressListForUser(userId)
    }
}