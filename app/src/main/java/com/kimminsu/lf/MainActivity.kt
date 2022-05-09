package com.kimminsu.lf

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kimminsu.lf.databinding.ActivityMainBinding
import com.kimminsu.lf.repository.PermissionRepository
import com.kimminsu.lf.view.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var mainFragment: MainFragment
    private lateinit var myProfileFragment: MyProfileFragment
    private lateinit var uploadFragment: UploadFragment
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    // 출력 테스트
    private lateinit var viewpostFragment: ViewPostFragment
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var managePermissions: PermissionRepository
    private val PermissionRequestCode = 123
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
        myProfileFragment = MyProfileFragment()
        uploadFragment = UploadFragment()
        // 테스트
        viewpostFragment = ViewPostFragment()
        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        val list = listOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // 나중에 카메라 파일 생성 후 코드 옮길 것
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    handleCameraImage(result.data)
                }
            }

        managePermissions = PermissionRepository(this, list, PermissionRequestCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            managePermissions.checkPermissions()
        }
        managePermissions.processPermissionsResult()
        if (!managePermissions.processPermissionsResult()) {
            //finish()
        }
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
            6 -> fragmentTransaction.replace(R.id.container, myProfileFragment)
            7 -> fragmentTransaction.replace(R.id.container, uploadFragment)
            // 출력 테스트
            8 -> fragmentTransaction.replace(R.id.container, viewpostFragment)
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
                        onFragmentChange(8)
                    }
                    R.id.navigation_bar_camera -> {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        resultLauncher.launch(cameraIntent)
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

    private fun handleCameraImage(intent: Intent?){
        val bitmap = intent?.extras?.get("data") as Bitmap

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
