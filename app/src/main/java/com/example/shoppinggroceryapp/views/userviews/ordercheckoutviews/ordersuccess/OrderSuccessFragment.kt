package com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.ordersuccess

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.MainActivity.Companion.cartId
import com.example.shoppinggroceryapp.MainActivity.Companion.userId
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.views.initialview.InitialFragment
import com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderdetail.OrderDetailFragment
import com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderlist.OrderListFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.order.DailySubscription
import com.example.shoppinggroceryapp.model.entities.order.MonthlyOnce
import com.example.shoppinggroceryapp.model.entities.order.TimeSlot
import com.example.shoppinggroceryapp.model.entities.order.WeeklyOnce
import com.example.shoppinggroceryapp.views.userviews.cartview.cart.CartFragment
import com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.PaymentFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton


class OrderSuccessFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view =  inflater.inflate(R.layout.fragment_order_confirmation, container, false)
        val orderSuccessViewModel = ViewModelProvider(this,
            OrderSuccessViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getRetailerDao())
        )[OrderSuccessViewModel::class.java]

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                restartApp()
            }

        })
        view.findViewById<MaterialToolbar>(R.id.orderSuccessToolbar).setNavigationOnClickListener {
//            onDestroyView()
            restartApp()
        }
        view.findViewById<MaterialButton>(R.id.materialButtonClose).setOnClickListener {
//            onDestroyView()
            restartApp()
        }
        var deliveryFrequency = arguments?.getString("deliveryFrequency")?:"Once"
        val address = CartFragment.selectedAddress
        val tmpCartId = cartId
        orderSuccessViewModel.placeOrder(tmpCartId,
            PaymentFragment.paymentMode,address!!.addressId,"Pending","Pending",deliveryFrequency)
        orderSuccessViewModel.orderedId.observe(viewLifecycleOwner){
            orderSuccessViewModel.getOrderAndCorrespondingCart(it.toInt())
            val selectedTimeSlot = arguments?.getInt("timeSlotInt")
            val dayOfWeek = arguments?.getInt("dayOfWeek")
            val dayOfMonth = arguments?.getInt("dayOfMonth")
            if(deliveryFrequency!="Once"){
                selectedTimeSlot?.let {selectedSlot ->
                    orderSuccessViewModel.addOrderToTimeSlot(TimeSlot(it.toInt(),selectedSlot))
                }
                if(deliveryFrequency=="Daily"){
                    orderSuccessViewModel.addDailySubscription(DailySubscription(it.toInt()))
                }
                else if(deliveryFrequency=="Weekly Once"){
                    dayOfWeek?.let {weekId->
                        orderSuccessViewModel.addWeeklySubscription(WeeklyOnce(it.toInt(),weekId))
                    }
                }
                else if(deliveryFrequency=="Monthly Once"){
                    dayOfMonth?.let {monthNumber->
                        orderSuccessViewModel.addMonthlySubscription(MonthlyOnce(it.toInt(),monthNumber))
                    }
                }

            }
        }
        orderSuccessViewModel.orderWithCart.observe(viewLifecycleOwner){

            if(it.values.isNotEmpty() && it.keys.isNotEmpty()) {
                for (i in it) {
                    OrderListFragment.selectedOrder = i.key
                    OrderListFragment.correspondingCartList = i.value
                }
                doFragmentTransaction()
            }
        }

        orderSuccessViewModel.updateAndAssignNewCart(cartId, userId.toInt())
        orderSuccessViewModel.newCart.observe(viewLifecycleOwner){
            cartId = it.cartId
        }

        return view
    }

    private fun doFragmentTransaction() {
        val orderDetailFrag = OrderDetailFragment()

        view?.findViewById<LinearLayout>(R.id.progressBarInOrderSummary)?.visibility = View.GONE
        view?.findViewById<LinearLayout>(R.id.tickMark)?.animate()
            ?.alpha(1f)
            ?.setDuration(100)
            ?.withEndAction {
                view?.findViewById<LinearLayout>(R.id.tickMark)?.visibility =View.VISIBLE
            }
            ?.start()
        orderDetailFrag.arguments = Bundle().apply {
            putBoolean("hideToolBar",true)
            putBoolean("hideCancelOrderButton",true)
        }
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
            )
            .replace(R.id.orderSummaryFragment,orderDetailFrag)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        InitialFragment.hideBottomNav.value = true
        InitialFragment.hideSearchBar.value = true
    }

    override fun onPause() {
        super.onPause()
//        restartApp()
    }

    private fun restartApp() {
        PaymentFragment.paymentMode =""
        val intent = Intent(context,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        InitialFragment.hideBottomNav.value = false
        InitialFragment.hideSearchBar.value = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}