package com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.views.initialview.InitialFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.user.UserEntity
import com.example.shoppinggroceryapp.helpers.inputvalidators.interfaces.InputChecker
import com.example.shoppinggroceryapp.helpers.inputvalidators.TextLayoutInputChecker
import com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.signup.SignUpFragment
import com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.forgotpassword.ForgotPasswordFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    private lateinit var emailPhoneTextLayout:TextInputLayout
    private lateinit var loginButton:MaterialButton
    private lateinit var passwordLayout:TextInputLayout
    private lateinit var emailPhoneText:TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var forgotPassword:MaterialButton
    private lateinit var signUp:MaterialButton
    private var login = false
    private lateinit var inputChecker: InputChecker
    private lateinit var handler: Handler
    private lateinit var loginViewModel: LoginViewModel
    private lateinit  var userEntityObserver:Observer<UserEntity?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inputChecker = TextLayoutInputChecker()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        initViews(view)
        setClickListeners()
        loginViewModel = ViewModelProvider(this,
            LoginViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getUserDao())
        )[LoginViewModel::class.java]
        setLoginViewModelObservers()
        setFocusChangeListeners()
        return view
    }

    private fun setFocusChangeListeners() {
        emailPhoneText.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                emailPhoneTextLayout.error = null
            }
        }
        password.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                passwordLayout.error = null
            }
        }
    }

    private fun setLoginViewModelObservers() {
        loginViewModel.userEntityName.observe(viewLifecycleOwner){
            if(it == null){
                Snackbar.make(requireView(),"User Not Found",Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(Color.argb(255,180,30,38))
                    show()
                }
            }
            else{
                loginViewModel.validateUser(emailPhoneText.text.toString().trim(),password.text.toString())
            }
        }

        loginViewModel.userEntity.observe(viewLifecycleOwner){
            if(it==null){
                Snackbar.make(requireView(),"InValid Password",Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(Color.argb(255,180,30,38))
                    show()
                }
            }
            else{
                val sharedPreferences = requireActivity().getSharedPreferences("freshCart",Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isSigned",true)
                editor.putBoolean("isRetailer",it.isRetailer)
                editor.putString("userFirstName",it.userFirstName)
                editor.putString("userLastName",it.userLastName)
                editor.putString("userEmail",it.userEmail)
                editor.putString("userPhone",it.userPhone)
                editor.putString("userId",it.userId.toString())
                editor.putString("userProfile",it.userImage)
                editor.apply()
                loginViewModel.assignCartForUser()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentBody, InitialFragment())
                    .commit()
            }
        }
    }

    private fun setClickListeners() {
        forgotPassword.setOnClickListener {
            password.text = null
            emailPhoneText.text = null
            passwordLayout.error = null
            emailPhoneTextLayout.error = null
            loginViewModel.userEntityName = MutableLiveData()
            loginViewModel.userEntity = MutableLiveData()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentBody, ForgotPasswordFragment())
                .addToBackStack("Sign Up Fragment")
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                .commit()
        }

        loginButton.setOnClickListener {
            emailPhoneTextLayout.error = inputChecker.emptyCheck(emailPhoneText)
            passwordLayout.error = inputChecker.emptyCheck(password)
            emailPhoneText.clearFocus()
            password.clearFocus()
            if(emailPhoneTextLayout.error == null && passwordLayout.error==null) {
                loginViewModel.isUser(emailPhoneText.text.toString().trim())
            }
        }


        signUp.setOnClickListener {
            password.text = null
            emailPhoneText.text = null
            passwordLayout.error = null
            emailPhoneTextLayout.error = null
            loginViewModel.userEntityName = MutableLiveData()
            loginViewModel.userEntity = MutableLiveData()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentBody, SignUpFragment())
                .addToBackStack("Sign Up Fragment")
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                .commit()
        }
    }

    private fun initViews(view: View) {
        emailPhoneText = view.findViewById(R.id.emailOrMobile)
        handler = Handler(Looper.getMainLooper())
        password = view.findViewById(R.id.password)
        emailPhoneTextLayout = view.findViewById(R.id.emailLayout)
        passwordLayout = view.findViewById(R.id.passwordLayout)
        signUp = view.findViewById(R.id.signUpBtn)
        loginButton = view.findViewById(R.id.loginButton)
        forgotPassword = view.findViewById(R.id.forgotPassword)
    }

    override fun onDetach() {
        super.onDetach()
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}