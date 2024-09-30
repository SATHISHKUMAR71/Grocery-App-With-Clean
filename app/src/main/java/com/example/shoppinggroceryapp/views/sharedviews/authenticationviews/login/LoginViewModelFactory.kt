package com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.model.dao.UserDao

class LoginViewModelFactory(var userDao: UserDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(userDao) as T
    }
}