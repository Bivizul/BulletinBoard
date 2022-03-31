package com.bivizul.bulletinboard.accounthelper

import android.widget.Toast
import com.bivizul.bulletinboard.MainActivity
import com.bivizul.bulletinboard.R
import com.bivizul.bulletinboard.dialoghelper.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AccountHelper(private val activity: MainActivity) {

    private lateinit var googleSignInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // task содержит user
                        sendEmailVerification(task.result?.user!!)
                        activity.uiUpdate(task.result?.user)
                    } else {
                        Toast.makeText(activity, R.string.sign_up_error, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // task содержит user
                        activity.uiUpdate(task.result?.user)
                    } else {
                        Toast.makeText(activity, R.string.sign_in_error, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    // создаем клиента
    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id)).build()
        return GoogleSignIn.getClient(activity, gso)
    }

    // запрос для входа
    fun singInWithGoogle() {
        googleSignInClient = getSignInClient()
        // создаем намерение клиента
        val intent = googleSignInClient.signInIntent
        // отправляем из активити и ждем результата GOOGLE_SIGN_IN_REQUEST_CODE
        activity.startActivityForResult(intent, GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    // отправка данных Google account в Firebase
    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        activity.mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Sign is done", Toast.LENGTH_LONG).show()
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
