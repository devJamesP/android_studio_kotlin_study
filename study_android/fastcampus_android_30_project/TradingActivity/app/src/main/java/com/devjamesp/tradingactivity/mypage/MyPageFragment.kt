package com.devjamesp.tradingactivity.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.devjamesp.tradingactivity.R
import com.devjamesp.tradingactivity.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment(R.layout.fragment_mypage) {

    private var binding: FragmentMypageBinding? = null

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMyPageBinding = FragmentMypageBinding.bind(view)
        binding = fragmentMyPageBinding

        fragmentMyPageBinding.signInOutButton.setOnClickListener {
            binding?.let { binding ->
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                if (auth.currentUser == null) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                successSignIn()
                            } else {
                                Toast.makeText(context, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    auth.signOut()
                    logoutState()
                    Toast.makeText(context, "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fragmentMyPageBinding.signUpButton.setOnClickListener {
            binding?.let { binding ->
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "회원가입을 성공했습니다.", Toast.LENGTH_SHORT).show()

                            // 회원가입 후 자동 로그인 상태가 되므로 UI 변경
                            loginState()
                        } else {
                            Toast.makeText(
                                context,
                                "회원가입을 실패했습니다. 다시 한번 확인해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        fragmentMyPageBinding.emailEditText.addTextChangedListener {
            binding?.let { binding ->
                val enable = binding.emailEditText.text.toString()
                    .isNotEmpty() && binding.passwordEditText.text.toString().isNotEmpty()
                binding.signInOutButton.isEnabled = enable
                binding.signUpButton.isEnabled = enable
            }
        }

        fragmentMyPageBinding.passwordEditText.addTextChangedListener {
            binding?.let { binding ->
                val enable = binding.emailEditText.text.toString()
                    .isNotEmpty() && binding.passwordEditText.text.toString().isNotEmpty()
                binding.signInOutButton.isEnabled = enable
                binding.signUpButton.isEnabled = enable
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            logoutState()
        } else {
            loginState()
        }
    }

    private fun successSignIn() {
        if (auth.currentUser == null) {
            Toast.makeText(context, "로그인에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            return
        } else {
            loginState()
            Toast.makeText(context, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginState() {
        binding?.let { binding ->
            binding.emailEditText.setText(auth.currentUser!!.email)
            binding.emailEditText.isEnabled = false

            binding.passwordEditText.setText("*".repeat(7))
            binding.passwordEditText.isEnabled = false

            binding.signInOutButton.isEnabled = true
            binding.signInOutButton.text = "로그아웃"
            binding.signUpButton.isEnabled = false
        }
    }

    private fun logoutState() {
        binding?.let { binding ->
            binding.emailEditText.text.clear()
            binding.emailEditText.isEnabled = true

            binding.passwordEditText.text.clear()
            binding.passwordEditText.isEnabled = true

            binding.signInOutButton.text = "로그인"
            binding.signInOutButton.isEnabled = false

            binding.signUpButton.isEnabled = false
        }
    }

}