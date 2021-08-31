package com.devJamesP.tinderactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()

        initSignUpButton()
        initLoginButton()
        initFaceBookButton()
        initEmailAndPasswordEditText()

    }

    private fun initSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this, "회원 가입에 성공했습니다. 로그인 버튼을 눌러 로그인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    private fun initLoginButton() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            Log.d("testt", "로그인버튼 셋업")
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        handleSuccessLogin()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "로그인에 실패하였습니다. 이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun initFaceBookButton() {
        val faceBookLoginButton = findViewById<LoginButton>(R.id.facebookLoginButton)

        /** facebook 로그인의 경우 회원가입이 되어 있지 않다면 회원가입 후 바로 로그인을 시켜줌
         * 그러나 회원가입 상태에서 로그인을 할 경우 토큰을 가지고 코딩한 과정대로 로그인 됨.
         */

        faceBookLoginButton.setPermissions("email", "public_profile")
        faceBookLoginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    // 로그인이 성공적
                    // credential :: facebook에 접근 할 토큰값
                    val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                    auth.signInWithCredential(credential) // 토큰을 넘겨줌
                        .addOnCompleteListener(this@LoginActivity) { task ->
                            if (task.isSuccessful) {
                                Log.d("testt", "토큰을 넘겨주고 해당 작업이 성공!")
                                handleSuccessLogin()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "페이스북 로그인이 실패했습니다.",
                                    Toast.LENGTH_SHORT
                                )
                            }
                        }
                }

                override fun onCancel() {
                    // 로그인하다가 취소
                    Log.d("testt", "2222")
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(this@LoginActivity, "페이스북 로그인이 실패했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        /** API 30 이상부터 */
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Log.d("testt", "페이스북 로그인 콜백 호출!")
                callbackManager.onActivityResult(REQUEST_CODE, result.resultCode, result.data)
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("testt", "페이스북 로그인 콜백 호출!")

        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun initEmailAndPasswordEditText() {
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable

        }

        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable
        }
    }

    private fun getInputEmail(): String =
        findViewById<EditText>(R.id.emailEditText).text.toString()

    private fun getInputPassword(): String =
        findViewById<EditText>(R.id.passwordEditText).text.toString()

    private fun handleSuccessLogin() {
        if (auth.currentUser == null) {
            Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            return
        } else {
            Log.d("testt", "로그인 성공!!")
            val userId = auth.currentUser?.uid.orEmpty()
            val currentUserDB = Firebase.database.reference.child("Users").child(userId)
            val user = mutableMapOf<String, Any>()
            user["userId"] = userId
            currentUserDB.updateChildren(user)

            finish()
        }
    }

    private fun handleFailedLogin() {

    }

    companion object {
        private const val REQUEST_CODE = 1111
    }
}