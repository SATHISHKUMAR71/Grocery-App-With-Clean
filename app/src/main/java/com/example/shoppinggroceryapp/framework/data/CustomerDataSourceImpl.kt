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
import com.example.shoppinggroceryapp.model.entities.user.User as UserEntity
import com.example.shoppinggroceryapp.model.entities.user.Address as AddressEntity
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequest as CustomerRequestEntity
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails as OrderDetailsEntity
import com.example.shoppinggroceryapp.model.dao.UserDao

class CustomerDataSourceImpl(private var userDao: UserDao):CustomerDataSource {
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
        TODO("Not yet implemented")
    }

    override fun getCartItems(cartId: Int): List<Cart> {
        TODO("Not yet implemented")
    }

    override fun getProductsByCartId(cartId: Int): List<Product> {
        TODO("Not yet implemented")
    }

    override fun addItemsToCart(cart: Cart) {
        TODO("Not yet implemented")
    }

    override fun getProductsWithCartId(cartId: Int): List<CartWithProductData> {
        TODO("Not yet implemented")
    }

    override fun getDeletedProductsWithCartId(cartId: Int): List<CartWithProductData> {
        TODO("Not yet implemented")
    }

    override fun removeProductInCart(cart: Cart) {
        TODO("Not yet implemented")
    }

    override fun updateCartMapping(cartMapping: CartMapping) {
        TODO("Not yet implemented")
    }

    override fun getSpecificCart(cartId: Int, productId: Int): Cart {
        TODO("Not yet implemented")
    }

    override fun updateCartItems(cart: Cart) {
        TODO("Not yet implemented")
    }

    override fun getBoughtProductsList(userId: Int): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersForUser(userID: Int): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersForUserWeeklySubscription(userID: Int): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersForUserDailySubscription(userID: Int): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersForUserMonthlySubscription(userID: Int): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersForUserNoSubscription(userID: Int): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersForRetailerWeeklySubscription(): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersRetailerDailySubscription(): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersForRetailerMonthlySubscription(): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrdersForRetailerNoSubscription(): List<OrderDetails> {
        TODO("Not yet implemented")
    }

    override fun getOrder(cartId: Int): OrderDetails {
        TODO("Not yet implemented")
    }

    override fun getOrderWithProductsWithOrderId(orderId: Int): Map<OrderDetails, List<CartWithProductData>> {
        TODO("Not yet implemented")
    }

    override fun getOrderDetailsWithOrderId(orderId: Int): OrderDetails {
        TODO("Not yet implemented")
    }

    override fun getProductById(productId: Long): Product {
        TODO("Not yet implemented")
    }

    override fun getRecentlyViewedProducts(user: Int): List<Int> {
        TODO("Not yet implemented")
    }

    override fun getOnlyProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getOfferedProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getProductByCategory(query: String): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getProductsByName(query: String): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getProductForQuery(query: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun getProductForQueryName(query: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun getBrandName(id: Long): String {
        TODO("Not yet implemented")
    }

    override fun updateParentCategory(parentCategory: ParentCategory) {
        TODO("Not yet implemented")
    }

    override fun getImagesForProduct(productId: Long): List<Images> {
        TODO("Not yet implemented")
    }

    override fun getAddress(addressId: Int): Address {
        TODO("Not yet implemented")
    }

    override fun addAddress(address: Address) {
        TODO("Not yet implemented")
    }

    override fun getAddressListForUser(userId: Int): List<Address> {
        TODO("Not yet implemented")
    }

    private fun convertUserToUserEntity(user: User):UserEntity{
        return UserEntity(user.userId,user.userImage,user.userFirstName,user.userLastName,user.userEmail,user.userPhone,
            user.userPassword,user.dateOfBirth,user.isRetailer)
    }
    private fun convertUserEntityToUser(user: UserEntity):User{
        return User(user.userId,user.userImage,user.userFirstName,user.userLastName,user.userEmail,user.userPhone,
            user.userPassword,user.dateOfBirth,user.isRetailer)
    }

    private fun convertOrderDetailsToOrderDetailsEntity(orderDetails: OrderDetails):OrderDetailsEntity{
        return OrderDetailsEntity(orderDetails.orderId,orderDetails.cartId,orderDetails.addressId,orderDetails.paymentMode,
            orderDetails.deliveryFrequency,orderDetails.paymentStatus,orderDetails.deliveryStatus,orderDetails.deliveryDate,orderDetails.orderedDate)
    }
    private fun convertOrderEntityToOrderDetails(orderDetailsEntity: OrderDetailsEntity):OrderDetails{
        return OrderDetails(orderDetailsEntity.orderId,orderDetailsEntity.cartId,orderDetailsEntity.addressId,orderDetailsEntity.paymentMode,
            orderDetailsEntity.deliveryFrequency,orderDetailsEntity.paymentStatus,orderDetailsEntity.deliveryStatus,orderDetailsEntity.deliveryDate,orderDetailsEntity.orderedDate)
    }
}