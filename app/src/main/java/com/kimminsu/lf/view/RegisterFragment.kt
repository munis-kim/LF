package com.kimminsu.lf.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kimminsu.lf.MainActivity
import com.kimminsu.lf.R
import com.kimminsu.lf.databinding.FragmentRegisterBinding
import com.kimminsu.lf.viewmodel.RegisterViewModel
import java.util.*

class RegisterFragment : Fragment() {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val registerBinding: FragmentRegisterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        val mainActivity = activity as MainActivity
        registerBinding.registerViewModel = registerViewModel

        val isRegisterObserver = Observer<Int> {
            if (it == 0) {
                Toast.makeText(context, "회원가입 성공", Toast.LENGTH_SHORT).show()
                mainActivity.onFragmentChange(1)
                registerViewModel.clearLiveData()

            } else if (it in 1..8)
                showErrorMessage(it)
        }

        registerViewModel.isRegisterLiveData.observe(viewLifecycleOwner, isRegisterObserver)
        registerViewModel.isGoLoginLiveData.observe(viewLifecycleOwner) {
            mainActivity.onFragmentChange(1)
        }
        return registerBinding.root
    }

    private fun showErrorMessage(errorCode: Int) {
        val errorMessage: String = when (errorCode) {
            1 -> "E-mail을 입력해주세요."
            2 -> "비밀번호를 입력해주세요."
            3 -> "비밀번호는 6자리 이상 입력해주세요."
            4 -> "비밀번호 확인을 입력해주세요."
            5 -> "일치하는 비밀번호를 입력해주세요."
            6 -> "닉네임을 입력해주세요."
            7 -> "이름을 입력해주세요."
            8 -> "중복되는 E-mail입니다."
            else -> "에러가 발생했습니다."
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}