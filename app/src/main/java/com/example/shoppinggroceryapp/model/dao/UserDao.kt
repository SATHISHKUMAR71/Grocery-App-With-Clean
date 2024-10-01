package com.example.shoppinggroceryapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinggroceryapp.model.dataclass.CustomerRequestWithName
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequestEntity
import com.example.shoppinggroceryapp.model.entities.order.CartEntity
import com.example.shoppinggroceryapp.model.entities.order.CartMappingEntity
import com.example.shoppinggroceryapp.model.entities.order.OrderDetailsEntity
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductDataEntity
import com.example.shoppinggroceryapp.model.entities.products.CategoryEntity
import com.example.shoppinggroceryapp.model.entities.products.DeletedProductListEntity
import com.example.shoppinggroceryapp.model.entities.products.ImagesEntity
import com.example.shoppinggroceryapp.model.entities.products.ParentCategoryEntity
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity
import com.example.shoppinggroceryapp.model.entities.search.SearchHistoryEntity
import com.example.shoppinggroceryapp.model.entities.user.UserEntity
import com.example.shoppinggroceryapp.model.entities.user.AddressEntity

@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userEntity: UserEntity):Long



    @Query("SELECT productId FROM ProductEntity Where productId=1")
    fun initDB():Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchQueryInDb(searchHistoryEntity: SearchHistoryEntity)

    @Query("SELECT * FROM SearchHistoryEntity Where SearchHistoryEntity.userId=:userId")
    fun getSearchHistory(userId: Int):List<SearchHistoryEntity>

    @Query("SELECT RecentlyViewedItemsEntity.productId FROM RecentlyViewedItemsEntity Where userId=:user")
    fun getRecentlyViewedProducts(user:Int):List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAddress(addressEntity: AddressEntity)

    @Update
    fun updateAddress(addressEntity: AddressEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Query("SELECT * FROM AddressEntity WHERE (AddressEntity.userId=:userId)")
    fun getAddressListForUser(userId:Int):List<AddressEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCustomerRequest(customerRequestEntity: CustomerRequestEntity)


    @Query("SELECT CustomerRequestEntity.helpId,CustomerRequestEntity.userId,CustomerRequestEntity.requestedDate,CustomerRequestEntity.orderId,CustomerRequestEntity.request,UserEntity.userFirstName,UserEntity.userLastName,UserEntity.userEmail,UserEntity.userPhone FROM CustomerRequestEntity JOIN UserEntity ON UserEntity.userId=CustomerRequestEntity.userId ORDER BY CustomerRequestEntity.helpId DESC")
    fun getDataFromCustomerReqWithName():List<CustomerRequestWithName>

    @Query("SELECT * FROM ProductEntity Order By productId DESC")
    fun getOnlyProducts():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '50' Order By productId DESC")
    fun getOnlyProduct50():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '40' Order By productId DESC")
    fun getOnlyProduct40():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '30' Order By productId DESC")
    fun getOnlyProduct30():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '20' Order By productId DESC")
    fun getOnlyProduct20():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '10' Order By productId DESC")
    fun getOnlyProduct10():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '50' and ProductEntity.categoryName=:category ")
    fun getOnlyProduct50WithCat(category:String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '40' and ProductEntity.categoryName=:category Order By productId DESC")
    fun getOnlyProduct40WithCat(category:String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '30' and ProductEntity.categoryName=:category Order By productId DESC")
    fun getOnlyProduct30WithCat(category:String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '20' and ProductEntity.categoryName=:category Order By productId DESC")
    fun getOnlyProduct20WithCat(category:String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer >= '10' and ProductEntity.categoryName=:category Order By productId DESC")
    fun getOnlyProduct10WithCat(category:String):List<ProductEntity>

    @Query("SELECT * FROM PRODUCT")
    fun getOnlyProductsLiveData():LiveData<MutableList<ProductEntity>>

    @Query("SELECT * FROM user WHERE ((userEmail=:emailOrPhone OR userPhone=:emailOrPhone) AND (userPassword=:password))")
    fun getUser(emailOrPhone:String,password:String):UserEntity

    @Query("SELECT * FROM USER WHERE USER.userId=:userId")
    fun getUserById(userId: Int):UserEntity

    @Query("SELECT * FROM USER WHERE USER.userPhone=:phone")
    fun getUserByPhone(phone: String):UserEntity

    @Query("SELECT * FROM USER WHERE USER.userPassword=:password")
    fun getUserByPassword(password: String):UserEntity


    @Query("SELECT * FROM USER WHERE USER.userEmail=:email")
    fun getUserByEmail(email: String):UserEntity


    @Query("SELECT userFirstName FROM user WHERE (userId=:id)")
    fun getUserFirstName(id:Int):String

    @Query("SELECT userLastName FROM user WHERE (userId=:id)")
    fun getUserLastName(id:Int):String

    @Query("SELECT * FROM PRODUCT WHERE ProductEntity.productId==:productId")
    fun getProductById(productId:Long):ProductEntity

    @Query("SELECT * FROM DeletedProductListEntity WHERE DeletedProductListEntity.productId==:productId")
    fun getDeletedProductById(productId:Long):DeletedProductListEntity

    @Query("SELECT * FROM user WHERE (user.userEmail=:emailOrPhone OR user.userPhone=:emailOrPhone)")
    fun getUserData(emailOrPhone:String):UserEntity


    @Query("SELECT * FROM ProductEntity WHERE ProductEntity.offer!='-1'")
    fun getOfferedProducts():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE (ProductEntity.categoryName =:query) ORDER BY ProductEntity.expiryDate DESC")
    fun getSortedExpiryHighProducts(query: String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE (ProductEntity.categoryName =:query) ORDER BY ProductEntity.expiryDate ASC")
    fun getSortedExpiryLowProducts(query: String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE (ProductEntity.categoryName =:query) ORDER BY ProductEntity.manufactureDate ASC")
    fun getSortedManufacturedLowProducts(query: String):List<ProductEntity>



    @Query("SELECT * FROM ProductEntity WHERE (ProductEntity.categoryName =:query) ORDER BY ProductEntity.manufactureDate DESC")
    fun getSortedManufacturedHighProducts(query: String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE (ProductEntity.categoryName =:query) ORDER BY ProductEntity.price DESC")
    fun getSortedPriceHighProducts(query: String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE (ProductEntity.categoryName =:query) ORDER BY ProductEntity.price ASC")
    fun getSortedPriceLowProducts(query: String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY ProductEntity.expiryDate DESC")
    fun getSortedExpiryHighProductsNoCat():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY ProductEntity.expiryDate ASC")
    fun getSortedExpiryLowProductsNoCat():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY ProductEntity.manufactureDate ASC")
    fun getSortedManufacturedLowProductsNoCat():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY ProductEntity.manufactureDate DESC")
    fun getSortedManufacturedHighProductsNoCat():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE offer>-1 ORDER BY ProductEntity.manufactureDate DESC")
    fun getSortedManufacturedHighProductsNoCatWithDiscount():List<ProductEntity>


    @Query("SELECT * FROM ProductEntity ORDER BY ProductEntity.price DESC")
    fun getSortedPriceHighProductsNoCat():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY ProductEntity.price ASC")
    fun getSortedPriceLowProductsNoCat():List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE(ProductEntity.categoryName =:query)")
    fun getProductByCategory(query:String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE(PRODUCT.productName=:query)")
    fun getProductsByName(query: String):List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE(ProductEntity.categoryName =:query)")
    fun getProductByCategoryLiveData(query:String):LiveData<MutableList<ProductEntity>>

    @Query("SELECT * FROM UserEntity JOIN AddressEntity ON UserEntity.userId = AddressEntity.userId WHERE UserEntity.userId=:id")
    fun getAddressDetailsForUser(id:Int):Map<UserEntity,List<AddressEntity>>

    @Query("SELECT CategoryEntity.categoryName FROM CategoryEntity WHERE CategoryEntity.categoryName LIKE '%' || :query || '%'")
    fun getProductForQuery(query: String):List<String>

    @Query("SELECT ProductEntity.productName FROM ProductEntity WHERE ProductEntity.productName LIKE '%' || :query || '%'")
    fun getProductForQueryName(query: String):List<String>

    @Query("SELECT * FROM AddressEntity WHERE AddressEntity.addressId=:addressId")
    fun getAddress(addressId:Int):AddressEntity

    @Query("SELECT ProductEntity.*,CategoryEntity.parentCategoryName,CategoryEntity.categoryDescription FROM ProductEntity JOIN CategoryEntity ON ProductEntity.categoryName=CategoryEntity.categoryName")
    fun getProducts():Map<CategoryEntity,List<ProductEntity>>

    @Query("SELECT * FROM CartMappingEntity WHERE CartMappingEntity.userId=:userId and CartMappingEntity.status='available'")
    fun getCartForUser(userId:Int):CartMappingEntity

    @Insert
    fun addCartForUser(cartMappingEntity:CartMappingEntity)

    @Query("Select * from OrderDetailsEntity Join CartMappingEntity on CartMappingEntity.cartId=OrderDetailsEntity.cartId Where CartMappingEntity.userId=:userId")
    fun getBoughtProductsList(userId: Int):List<OrderDetailsEntity>

    @Query("SELECT * From CartMappingEntity Where CartMappingEntity.userId=:userId and CartMappingEntity.status!='available'")
    fun getCartId(userId:Int):List<CartMappingEntity>

    @Query("SELECT * FROM CartEntity WHERE CartEntity.cartId=:cartId")
    fun getCartItems(cartId:Int):List<CartEntity>

    @Query("SELECT ProductEntity.* FROM CartEntity Join ProductEntity ON ProductEntity.productId = CartEntity.productId WHERE CartEntity.cartId=:cartId")
    fun getProductsByCartId(cartId:Int):List<ProductEntity>


    @Query("SELECT ProductEntity.* FROM CartEntity Join ProductEntity ON ProductEntity.productId = CartEntity.productId WHERE CartEntity.cartId=:cartId")
    fun getProductsByCartIdLiveData(cartId:Int):LiveData<MutableList<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItemsToCart(cartEntity:CartEntity)

    @Query("SELECT ProductEntity.* FROM CartEntity JOIN ProductEntity ON CartEntity.productId=ProductEntity.productId WHERE CartEntity.cartId-:cartId")
    fun getCartWithProducts(cartId:Int):List<ProductEntity>


    @Query(
        "SELECT ProductEntity.mainImage AS mainImage,ProductEntity.productName AS productName,ProductEntity.productDescription as productDescription,CartEntity.totalItems as totalItems,CartEntity.unitPrice as unitPrice,ProductEntity.manufactureDate AS manufactureDate" +
                ",ProductEntity.expiryDate as expiryDate,ProductEntity.productQuantity as productQuantity,BrandDataEntity.brandName as brandName FROM CartEntity Join ProductEntity ON ProductEntity.productId=CartEntity.productId JOIN BrandDataEntity ON BrandDataEntity.brandId=ProductEntity.brandId WHERE CartEntity.cartId=:cartId"
    )
    fun getProductsWithCartId(cartId:Int):List<CartWithProductDataEntity>

    @Query(
        "SELECT DeletedProductListEntity.mainImage AS mainImage,DeletedProductListEntity.productName AS productName,DeletedProductListEntity.productDescription as productDescription,CartEntity.totalItems as totalItems,CartEntity.unitPrice as unitPrice,DeletedProductListEntity.manufactureDate AS manufactureDate" +
                ",DeletedProductListEntity.expiryDate as expiryDate,DeletedProductListEntity.productQuantity as productQuantity,BrandDataEntity.brandName as brandName FROM CartEntity Join DeletedProductListEntity ON DeletedProductListEntity.productId=CartEntity.productId JOIN BrandDataEntity ON BrandDataEntity.brandId=DeletedProductListEntity.brandId WHERE CartEntity.cartId=:cartId"
    )
    fun getDeletedProductsWithCartId(cartId:Int):List<CartWithProductDataEntity>

    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE CartMappingEntity.userId=:userID ORDER BY orderId DESC")
    fun getOrdersForUser(userID:Int):List<OrderDetailsEntity>

    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE (CartMappingEntity.userId=:userID and OrderDetailsEntity.deliveryFrequency='Weekly Once') ORDER BY orderId DESC")
    fun getOrdersForUserWeeklySubscription(userID:Int):List<OrderDetailsEntity>

    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE (OrderDetailsEntity.deliveryFrequency='Weekly Once') ORDER BY orderId DESC")
    fun getOrdersForRetailerWeeklySubscription():List<OrderDetailsEntity>

    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE (CartMappingEntity.userId=:userID and OrderDetailsEntity.deliveryFrequency='Daily') ORDER BY orderId DESC")
    fun getOrdersForUserDailySubscription(userID:Int):List<OrderDetailsEntity>

    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE (OrderDetailsEntity.deliveryFrequency='Daily') ORDER BY orderId DESC")
    fun getOrdersRetailerDailySubscription():List<OrderDetailsEntity>

    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE (CartMappingEntity.userId=:userID and OrderDetailsEntity.deliveryFrequency='Monthly Once') ORDER BY orderId DESC")
    fun getOrdersForUserMonthlySubscription(userID:Int):List<OrderDetailsEntity>

    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE (OrderDetailsEntity.deliveryFrequency='Monthly Once') ORDER BY orderId DESC")
    fun getOrdersForRetailerMonthlySubscription():List<OrderDetailsEntity>

    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE (CartMappingEntity.userId=:userID and OrderDetailsEntity.deliveryFrequency='Once') ORDER BY orderId DESC")
    fun getOrdersForUserNoSubscription(userID:Int):List<OrderDetailsEntity>


    @Query("SELECT OrderDetailsEntity.* FROM OrderDetailsEntity JOIN CartMappingEntity ON CartMappingEntity.cartId=OrderDetailsEntity.cartId WHERE (OrderDetailsEntity.deliveryFrequency='Once') ORDER BY orderId DESC")
    fun getOrdersForRetailerNoSubscription():List<OrderDetailsEntity>

    @Query("SELECT * FROM OrderDetailsEntity WHERE OrderDetailsEntity.cartId=:cartId")
    fun getOrder(cartId:Int):OrderDetailsEntity

    @Query("SELECT * FROM OrderDetailsEntity WHERE OrderDetailsEntity.orderId=:orderId")
    fun getOrderDetails(orderId:Int):OrderDetailsEntity

    @Query("SELECT * FROM ImagesEntity WHERE productId=:productId")
    fun getImagesForProduct(productId: Long):List<ImagesEntity>

    @Query(
        "SELECT OrderDetailsEntity.*,ProductEntity.mainImage AS mainImage,ProductEntity.productName AS productName,ProductEntity.productDescription as productDescription,CartEntity.totalItems as totalItems," +
                "CartEntity.unitPrice as unitPrice,ProductEntity.manufactureDate AS manufactureDate,ProductEntity.expiryDate as expiryDate,ProductEntity.productQuantity as productQuantity,BrandDataEntity.brandName as brandName FROM OrderDetailsEntity JOIN CartEntity ON CartEntity.cartId=OrderDetailsEntity.cartId JOIN ProductEntity ON ProductEntity.productId=CartEntity.productId JOIN BrandDataEntity ON BrandDataEntity.brandId=ProductEntity.brandId WHERE OrderDetailsEntity.orderId=:orderId"
    )
    fun getOrderWithProductsWithOrderId(orderId: Int):Map<OrderDetailsEntity,List<CartWithProductDataEntity>>

    @Update
    fun updateParentCategory(parentCategoryEntity: ParentCategoryEntity)



    @Delete
    fun removeProductInCart(cartEntity: CartEntity)

    @Query("SELECT BrandDataEntity.brandName FROM BrandDataEntity where BrandDataEntity.brandId=:id")
    fun getBrandName(id:Long):String

    @Update
    fun updateCartMapping(cartMappingEntity: CartMappingEntity)
    @Query("SELECT * FROM CartEntity WHERE CartEntity.cartId=:cartId and CartEntity.productId=:productId")
    fun getSpecificCart(cartId:Int,productId:Int):CartEntity
    @Update
    fun updateCartItems(cartEntity: CartEntity)

}