package com.example.shoppinggroceryapp.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinggroceryapp.model.entities.order.DailySubscriptionEntity
import com.example.shoppinggroceryapp.model.entities.order.MonthlyOnceEntity
import com.example.shoppinggroceryapp.model.entities.order.OrderDetailsEntity
import com.example.shoppinggroceryapp.model.entities.order.TimeSlotEntity
import com.example.shoppinggroceryapp.model.entities.order.WeeklyOnceEntity
import com.example.shoppinggroceryapp.model.entities.products.BrandDataEntity
import com.example.shoppinggroceryapp.model.entities.products.CategoryEntity
import com.example.shoppinggroceryapp.model.entities.products.DeletedProductListEntity
import com.example.shoppinggroceryapp.model.entities.products.ImagesEntity
import com.example.shoppinggroceryapp.model.entities.products.ParentCategoryEntity
import com.example.shoppinggroceryapp.model.entities.products.ProductEntity
import com.example.shoppinggroceryapp.model.entities.recentlyvieweditems.RecentlyViewedItemsEntity

@Dao
interface RetailerDao:UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addParentCategory(parentCategoryEntity: ParentCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSubCategory(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewBrand(brandDataEntity: BrandDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTimeSlot(timeSlotEntity: TimeSlotEntity)

    @Update
    fun updateTimeSlot(timeSlotEntity: TimeSlotEntity)


    @Delete
    fun deleteFromWeeklySubscription(weeklyOnceEntity: WeeklyOnceEntity)

    @Delete
    fun deleteFromMonthlySubscription(monthlyOnceEntity: MonthlyOnceEntity)

    @Delete
    fun deleteFromDailySubscription(dailySubscriptionEntity: DailySubscriptionEntity)

    @Query("SELECT * FROM DailySubscriptionEntity")
    fun getDailySubscription():List<DailySubscriptionEntity>

    @Query("SELECT * FROM TimeSlotEntity")
    fun getOrderTimeSlot():List<TimeSlotEntity>

    @Query("SELECT * FROM WeeklyOnceEntity")
    fun getWeeklySubscriptionList():List<WeeklyOnceEntity>

    @Query("SELECT * FROM MonthlyOnceEntity")
    fun getMonthlySubscriptionList():List<MonthlyOnceEntity>

    @Query("SELECT * FROM WeeklyOnceEntity Where WeeklyOnceEntity.orderId=:orderId")
    fun getOrderedDayForWeekSubscription(orderId:Int):WeeklyOnceEntity

    @Query("SELECT * FROM DailySubscriptionEntity Where DailySubscriptionEntity.orderId=:orderId")
    fun getOrderForDailySubscription(orderId:Int):DailySubscriptionEntity

    @Query("SELECT * FROM TimeSlotEntity Where TimeSlotEntity.orderId=:orderId")
    fun getOrderedTimeSlot(orderId:Int):TimeSlotEntity

    @Query("SELECT * FROM MonthlyOnceEntity Where MonthlyOnceEntity.orderId=:orderId")
    fun getOrderedDayForMonthlySubscription(orderId:Int):MonthlyOnceEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMonthlyOnceSubscription(monthlyOnceEntity: MonthlyOnceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeeklyOnceSubscription(weeklyOnceEntity: WeeklyOnceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDailySubscription(dailySubscriptionEntity: DailySubscriptionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM ProductEntity ORDER BY productId DESC")
    fun getLastProduct():ProductEntity


    @Update
    fun updateProduct(productEntity: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProductInRecentlyViewedItems(recentlyViewedItem: RecentlyViewedItemsEntity)

    @Query("SELECT * FROM RecentlyViewedItemsEntity WHERE RecentlyViewedItemsEntity.productId=:productId and RecentlyViewedItemsEntity.userId=:user")
    fun getProductsInRecentList(productId:Long,user:Int):RecentlyViewedItemsEntity

//    @Query("SELECT * FROM ImagesEntity WHERE productId=:productId")
//    fun getImagesForProduct(productId: Long):List<ImagesEntity>

    @Query("SELECT * FROM ImagesEntity WHERE ImagesEntity.images=:image")
    fun getSpecificImage(image:String):ImagesEntity

    @Delete
    fun deleteImage(imagesEntity: ImagesEntity)

    @Query("SELECT * FROM OrderDetailsEntity ORDER BY orderId DESC")
    fun getOrderDetails():List<OrderDetailsEntity>

    @Insert
    fun addImagesInDb(imagesEntity: ImagesEntity)

    @Update
    fun updateOrderDetails(orderDetailsEntity: OrderDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addOrder(order:OrderDetailsEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeletedProduct(deletedProductListEntity: DeletedProductListEntity)

    @Delete
    fun deleteProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM BrandDataEntity Where BrandDataEntity.brandName=:brandName")
    fun getBrandWithName(brandName:String):BrandDataEntity

    @Query("SELECT ParentCategoryEntity.parentCategoryImage FROM ParentCategoryEntity JOIN CategoryEntity ON CategoryEntity.parentCategoryName=ParentCategoryEntity.parentCategoryName Where categoryName=:parentCategoryName")
    fun getParentCategoryImage(parentCategoryName: String):String

    @Query("SELECT ParentCategoryEntity.parentCategoryImage FROM ParentCategoryEntity Where parentCategoryName=:parentCategoryName")
    fun getParentCategoryImageForParent(parentCategoryName: String):String

}