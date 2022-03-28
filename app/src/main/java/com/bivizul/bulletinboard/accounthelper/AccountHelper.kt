package com.bivizul.bulletinboard.accounthelper

import android.widget.Toast
import com.bivizul.bulletinboard.MainActivity
import com.bivizul.bulletinboard.R
import com.google.firebase.auth.FirebaseUser

class AccountHelper(private val activity: MainActivity) {

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result.user!!)
                    } else {
                        Toast.makeText(activity, R.string.sign_up_error, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, R.string.send_verification_done, Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(activity, R.string.send_verification_email_error, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}