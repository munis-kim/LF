package com.kimminsu.lf.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.databinding.FragmentLoginBinding
import com.kimminsu.lf.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loginBinding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.hideBar(true, true)
        loginBinding.loginViewModel = loginViewModel
        val isLoginObserver = Observer<Int>{
            if(it == 0) {
                Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                loginViewModel.clearLiveData()
                mainActivity.onFragmentChange(3)
            }
            else if(it in 1..3)
                showErrorMessage(it)
        }

        loginViewModel.isLoginLiveData.observe(viewLifecycleOwner, isLoginObserver)
        loginViewModel.isGoRegisterLiveData.observe(viewLifecycleOwner, Observer{
            mainActivity.onFragmentChange(2)
        })
        return loginBinding.root
    }

    private fun showErrorMessage(errorCode: Int){
        val errorMessage: String = when (errorCode){
            1 -> "아이디를 입력해주세요."
            2 -> "비밀번호를 입력해주세요."
            3 -> "아이디 또는 비밀번호가 틀렸습니다."
            else -> "에러가 발생했습니다."
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}