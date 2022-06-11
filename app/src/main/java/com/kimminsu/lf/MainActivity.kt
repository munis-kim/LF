package com.kimminsu.lf

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.util.maps.helper.Utility
import com.kimminsu.lf.databinding.ActivityMainBinding
import com.kimminsu.lf.utils.SetPermission
import com.kimminsu.lf.view.*
import com.kimminsu.lf.viewmodel.MainViewModel
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var mainFragment: MainFragment
    private lateinit var myProfileFragment: MyProfileFragment
    private lateinit var uploadFragment: UploadFragment
    private lateinit var mapFragment: MapFragment
    private lateinit var viewpostFragment: ViewPostFragment
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var managePermissions: SetPermission
    private lateinit var search: SearchView
    private val PermissionRequestCode = 123

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
        myProfileFragment = MyProfileFragment()
        uploadFragment = UploadFragment()
        mapFragment = MapFragment()
        viewpostFragment = ViewPostFragment()
        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        val topBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.topToolBar)
        search = findViewById(R.id.search_view)
        setContentView(binding.root)
        val list = listOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        managePermissions = SetPermission(this, list, PermissionRequestCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            managePermissions.checkPermissions()
        }
        managePermissions.processPermissionsResult()
        if (!managePermissions.processPermissionsResult()) {
            exitProcess(0)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query != null) {
                    mainFragment.search(query)
                    true
                } else false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "")
                    mainFragment.search(newText)
                return false
            }
        })
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
            4 -> fragmentTransaction.replace(R.id.container, mapFragment).addToBackStack(null)
            6 -> fragmentTransaction.replace(R.id.container, myProfileFragment)
            7 -> fragmentTransaction.replace(R.id.container, uploadFragment).addToBackStack(null)
            // 출력 테스트
            8 -> fragmentTransaction.replace(R.id.container, viewpostFragment).addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    fun hideBar(state_bottom: Boolean, state_top: Boolean) {
        val bottomBar =
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigation)
        val topBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.topToolBar)
        bottomBar.visibility = if (state_bottom) View.GONE else View.VISIBLE
        topBar.visibility = if (state_top) View.GONE else View.VISIBLE
    }

    private fun onClickBottomBar() {
        binding.bottomNavigation.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_bar_home -> {
                        onFragmentChange(3)
                    }
                    R.id.navigation_bar_GPS -> {
                        onFragmentChange(4)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty()) {
                    var isAllGranted = true
                    for (grant in grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break;
                        }
                    }
                }
            }
        }
    }


}
