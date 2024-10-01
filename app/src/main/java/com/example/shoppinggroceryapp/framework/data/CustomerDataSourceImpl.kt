package com.example.shoppinggroceryapp.framework.data

import com.core.data.datasource.customerdatasource.CustomerDataSource
import com.core.domain.help.CustomerRequest
import com.core.domain.order.Cart
import com.core.domain.order.CartMapping
import com.core.domain.order.OrderDetails
import com.core.domain.products.CartWithProductData
import com.core.domain.products.Images
import com.core.domain.products.ParentCategory
import com.core.domain.products.Product
import com.core.domain.user.Address
import com.core.domain.user.User
import com.example.shoppinggroceryapp.model.entities.user.UserEntity as UserEntity
import com.example.shoppinggroceryapp.model.entities.user.AddressEntity as AddressEntity
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequestEntity as CustomerRequestEntity
import com.example.shoppinggroceryapp.model.entities.order.OrderDetailsEntity as OrderDetailsEntity
import com.example.shoppinggroceryapp.model.entities.order.CartEntity as CartEntity
import com.example.shoppinggroceryapp.model.entities.order.CartMappingEntity as CartMappingEntity
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.products.ParentCategoryEntity
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity

class CustomerDataSourceImpl(private var userDao: UserDao):CustomerDataSource,ConvertionHelper() {
    override fun addNewUser(user: User) {
        userDao.addUser(convertUserToUserEntity(user))
    }

    override fun updateUser(user: User) {
        userDao.updateUser(convertUserToUserEntity(user))
    }

    override fun updateAddress(address: Address) {
        userDao.updateAddress(AddressEntity(address.addressId,address.userId,address.addressContactName,
            address.addressContactNumber,address.buildingName,address.streetName,address.city,address.state,address.country,address.postalCode))
    }

    override fun addCustomerRequest(customerRequest: CustomerRequest) {
        userDao.addCustomerRequest(CustomerRequestEntity(customerRequest.helpId,customerRequest.userId,customerRequest.requestedDate,
            customerRequest.orderId,customerRequest.request))
    }

    override fun getUser(emailOrPhone: String, password: String): User {
        val user =userDao.getUser(emailOrPhone,password)
        return convertUserEntityToUser(user)
    }

    override fun getUserData(emailOrPhone: String): User {
        return convertUserEntityToUser(userDao.getUserData(emailOrPhone))
    }

    override fun getOrderDetails(orderId: Int): OrderDetails {
        return convertOrderEntityToOrderDetails(userDao.getOrderDetails(orderId))
    }

    override fun getCartForUser(userId: Int): CartMapping {
        val cartMappingData = userDao.getCartForUser(userId)
        return CartMapping(cartMappingData.cartId,cartMappingData.userId,cartMappingData.status)
    }

    override fun addCartForUser(cartMapping: CartMapping) {
        userDao.addCartForUser(CartMappingEntity(cartMapping.cartId,cartMapping.userId,cartMapping.status))
    }

    override fun getCartItems(cartId: Int): List<Cart> {
        return userDao.getCartItems(cartId).map { Cart(it.cartId,it.productId,it.totalItems,it.unitPrice) }
    }

    override fun getProductsByCartId(cartId: Int): List<Product> {
        return userDao.getProductsByCartId(cartId).map { Product(it.productId,it.brandId,it.categoryName,it.productName,it.productDescription
        ,it.price,it.offer,it.productQuantity,it.mainImage,it.isVeg,it.manufactureDate,it.expiryDate,it.availableItems) }
    }

    override fun addItemsToCart(cart: Cart) {
        userDao.addItemsToCart(CartEntity(cart.cartId,cart.productId,cart.totalItems,cart.unitPrice))
    }

    override fun getProductsWithCartId(cartId: Int): List<CartWithProductData> {
        return (userDao.getProductsWithCartId(cartId)).map { CartWithProductData(it.mainImage,it.productName,it.productDescription,it.totalItems,it.unitPrice
        ,it.manufactureDate,it.expiryDate,it.productQuantity,it.brandName) }
    }

    override fun getDeletedProductsWithCartId(cartId: Int): List<CartWithProductData> {
        return userDao.getDeletedProductsWithCartId(cartId).map { CartWithProductData(it.mainImage,it.productName,it.productDescription,it.totalItems,it.unitPrice
            ,it.manufactureDate,it.expiryDate,it.productQuantity,it.brandName) }
    }

    override fun removeProductInCart(cart: Cart) {
        userDao.removeProductInCart(CartEntity(cart.cartId,cart.productId,cart.totalItems,cart.unitPrice))
    }

    override fun updateCartMapping(cartMapping: CartMapping) {
        userDao.updateCartMapping(CartMappingEntity(cartMapping.cartId,cartMapping.userId,cartMapping.status))
    }

    override fun getSpecificCart(cartId: Int, productId: Int): Cart {
        var cart = userDao.getSpecificCart(cartId,productId)
        return Cart(cart.cartId,cart.productId,cart.totalItems,cart.unitPrice)
    }

    override fun updateCartItems(cart: Cart) {
        userDao.updateCartItems(CartEntity(cart.cartId,cart.productId,cart.totalItems,cart.unitPrice))
    }

    override fun getBoughtProductsList(userId: Int): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getBoughtProductsList(userId))
    }

    override fun getOrdersForUser(userID: Int): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersForUser(userID))
    }

    override fun getOrdersForUserWeeklySubscription(userID: Int): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersForUserWeeklySubscription(userID))
    }

    override fun getOrdersForUserDailySubscription(userID: Int): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersForUserDailySubscription(userID))
    }

    override fun getOrdersForUserMonthlySubscription(userID: Int): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersForUserMonthlySubscription(userID))
    }

    override fun getOrdersForUserNoSubscription(userID: Int): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersForUserNoSubscription(userID))
    }

    override fun getOrdersForRetailerWeeklySubscription(): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersForRetailerWeeklySubscription())
    }

    override fun getOrdersRetailerDailySubscription(): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersRetailerDailySubscription())
    }

    override fun getOrdersForRetailerMonthlySubscription(): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersForRetailerMonthlySubscription())
    }

    override fun getOrdersForRetailerNoSubscription(): List<OrderDetails> {
        return convertOrderDetailsEntityToOrderDetails(userDao.getOrdersForRetailerNoSubscription())
    }

    override fun getOrder(cartId: Int): OrderDetails {
        return convertOrderEntityToOrderDetails(userDao.getOrder(cartId))
    }

    override fun getOrderWithProductsWithOrderId(orderId: Int): Map<OrderDetails, List<CartWithProductData>> {
        val map:MutableMap<OrderDetails, List<CartWithProductData>> = mutableMapOf()
        val orderDetailsMap = userDao.getOrderWithProductsWithOrderId(orderId)
        for( i in orderDetailsMap){
            map[convertOrderEntityToOrderDetails(i.key)] = i.value.map { CartWithProductData(it.mainImage,it.productName,it.productDescription,it.totalItems,it.unitPrice,
                it.manufactureDate,it.expiryDate,it.productQuantity,it.brandName) }
        }
        return map
    }

    override fun getOrderDetailsWithOrderId(orderId: Int): OrderDetails {
        return convertOrderEntityToOrderDetails(userDao.getOrderDetails(orderId))
    }

    override fun getProductById(productId: Long): Product {
        val product = userDao.getProductById(productId)
        return convertProductEntityToProduct(product)
    }

    override fun getRecentlyViewedProducts(user: Int): List<Int> {
        return userDao.getRecentlyViewedProducts(user)
    }

    override fun getOnlyProducts(): List<Product> {
        return userDao.getOnlyProducts().map { convertProductEntityToProduct(it) }
    }

    override fun getOfferedProducts(): List<Product> {
        return userDao.getOfferedProducts().map { convertProductEntityToProduct(it) }
    }

    override fun getProductByCategory(query: String): List<Product> {
        return userDao.getProductByCategory(query).map { convertProductEntityToProduct(it) }
    }

    override fun getProductsByName(query: String): List<Product> {
        return userDao.getProductsByName(query).map { convertProductEntityToProduct(it) }
    }

    override fun getProductForQuery(query: String): List<String> {
        return userDao.getProductForQuery(query)
    }

    override fun getProductForQueryName(query: String): List<String> {
        return userDao.getProductForQueryName(query)
    }

    override fun getBrandName(id: Long): String {
        return userDao.getBrandName(id)
    }

    override fun updateParentCategory(parentCategory: ParentCategory) {
        userDao.updateParentCategory(ParentCategoryEntity(parentCategory.parentCategoryName,parentCategory.parentCategoryImage,parentCategory.parentCategoryDescription,parentCategory.isEssential))
    }

    override fun getImagesForProduct(productId: Long): List<Images> {
        return userDao.getImagesForProduct(productId).map { Images(it.imageId,it.productId,it.images) }
    }

    override fun getAddress(addressId: Int): Address {
        val address = userDao.getAddress(addressId)
        return Address(address.addressId,address.userId,address.addressContactName,address.addressContactNumber,address.buildingName
        ,address.streetName,address.city,address.state,address.country,address.postalCode)
    }

    override fun addAddress(address: Address) {
        userDao.addAddress(AddressEntity(address.addressId,address.userId,address.addressContactName,address.addressContactNumber,address.buildingName
            ,address.streetName,address.city,address.state,address.country,address.postalCode))
    }

    override fun getAddressListForUser(userId: Int): List<Address> {
        return userDao.getAddressListForUser(userId).map { address -> Address(address.addressId,address.userId,address.addressContactName,address.addressContactNumber,address.buildingName
            ,address.streetName,address.city,address.state,address.country,address.postalCode) }
    }

}