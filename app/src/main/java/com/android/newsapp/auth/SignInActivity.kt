package com.android.newsapp.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.android.newsapp.R
import com.android.newsapp.ui.NewsActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        tv_forgot_password.setOnClickListener(this)

        btn_login.setOnClickListener(this)

        tv_register.setOnClickListener(this)

        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_forgot_password -> {

                    val intent = Intent(this, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {

                    logInRegisteredUser()
                }

                R.id.tv_register -> {
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))


            val email = et_email.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }


            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                       userLoggedInSuccess()
                    } else {

                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }



    fun userLoggedInSuccess() {


        hideProgressDialog()

            startActivity(Intent(this, NewsActivity::class.java))

        finish()
    }
}