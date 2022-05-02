package com.kimminsu.lf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kimminsu.lf.R
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kimminsu.lf.view.LoginFragment
import com.kimminsu.lf.view.RegisterFragment

class MainActivity : AppCompatActivity() {

    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginFragment = LoginFragment()
        registerFragment = RegisterFragment()

        val isLogin = Firebase.auth.currentUser
        if(isLogin == null){
            onFragmentChange(1)
        }
    }

    fun onFragmentChange(code: Int){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when(code){
            1 -> fragmentTransaction.replace(R.id.container, loginFragment)
            2 -> fragmentTransaction.replace(R.id.container, registerFragment)
        }
        fragmentTransaction.commit()
    }

}