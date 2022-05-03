package com.kimminsu.lf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kimminsu.lf.databinding.ActivityMainBinding
import com.kimminsu.lf.view.LoginFragment
import com.kimminsu.lf.view.MainFragment
import com.kimminsu.lf.view.RegisterFragment
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var mainFragment: MainFragment
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private var pressedTime: Long = 0

    override fun onBackPressed() {
        if (pressedTime == 0L) {
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            pressedTime = System.currentTimeMillis()
        } else {
            var seconds: Int = (System.currentTimeMillis() - pressedTime).toInt()

            if (seconds > 2000) {
                Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                pressedTime = 0
            } else {
                super.onBackPressed()
                Firebase.auth.signOut()
                moveTaskToBack(true)
                android.os.Process.killProcess(android.os.Process.myPid())
                exitProcess(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Firebase.auth.signOut()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Firebase.auth.signOut()
        loginFragment = LoginFragment()
        registerFragment = RegisterFragment()
        mainFragment = MainFragment()
        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        onClickBottomBar()
    }

    override fun onStart() {
        super.onStart()
        val isLogin = auth.currentUser
        if (isLogin == null) {
            onFragmentChange(1)
        }
    }

    fun onFragmentChange(code: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (code) {
            1 -> fragmentTransaction.replace(R.id.container, loginFragment)
            2 -> fragmentTransaction.replace(R.id.container, registerFragment)
            3 -> fragmentTransaction.replace(R.id.container, mainFragment)
        }
        fragmentTransaction.commit()
    }

    fun hideBar(state: Boolean) {
        val bottomBar =
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigation)
        val topBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.topToolBar)
        bottomBar.visibility = if (state) View.GONE else View.VISIBLE
        topBar.visibility = if (state) View.GONE else View.VISIBLE
    }

    fun onClickBottomBar() {
        binding.bottomNavigation.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_bar_home -> {
                        onFragmentChange(3)
                    }
                    R.id.navigation_bar_camera -> {
                        onFragmentChange(4)
                    }
                    R.id.navigation_bar_GPS -> {
                        onFragmentChange(5)
                    }
                    R.id.navigation_bar_Profile -> {
                        onFragmentChange(6)
                    }
                }
                true
            }
            selectedItemId = R.id.navigation_bar_home
        }
    }
}
