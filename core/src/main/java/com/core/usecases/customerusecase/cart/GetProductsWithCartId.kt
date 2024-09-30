package com.core.usecases.customerusecase.cart

import com.core.data.repository.CustomerRepository
import com.core.domain.products.CartWithProductData

class GetProductsWithCartId(private val customerRepository: CustomerRepository){
    fun invoke(cartId:Int):List<CartWithProductData>{
        return customerRepository.getProductsWithCartId(cartId)
    }
}