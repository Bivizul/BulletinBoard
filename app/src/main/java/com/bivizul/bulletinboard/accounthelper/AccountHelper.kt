package com.bivizul.bulletinboard.accounthelper

import android.util.Log
import android.widget.Toast
import com.bivizul.bulletinboard.MainActivity
import com.bivizul.bulletinboard.R
import com.bivizul.bulletinboard.constants.FirebaseAuthConstants
import com.bivizul.bulletinboard.dialoghelper.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*

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
                        // Если что-то не так, то проверяем ошибки

                        //Toast.makeText(activity, R.string.sign_up_error, Toast.LENGTH_LONG).show()
                        Log.d("MyLog", "Exception: ${task.exception}")
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            val exception = task.exception as FirebaseAuthUserCollisionException
                            //Log.d("MyLog","Exception: ${exception.errorCode}")
                            if (exception.errorCode == FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE) {
                                /* Toast.makeText(
                                     activity,
                                     FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE,
                                     Toast.LENGTH_LONG
                                 ).show()*/

                                linkEmailToG(email, password)
                            }
                        } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            val exception =
                                task.exception as FirebaseAuthInvalidCredentialsException
                            //Log.d("MyLog", "Exception: ${exception.errorCode}")
                            if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                                Toast.makeText(
                                    activity,
                                    FirebaseAuthConstants.ERROR_INVALID_EMAIL,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        if (task.exception is FirebaseAuthWeakPasswordException) {
                            val exception =
                                task.exception as FirebaseAuthWeakPasswordException
                            Log.d("MyLog", "Exception: ${exception.errorCode}")
                            if (exception.errorCode == FirebaseAuthConstants.ERROR_WEAK_PASSWORD) {
                                Toast.makeText(
                                    activity,
                                    FirebaseAuthConstants.ERROR_WEAK_PASSWORD,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
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
                        // Если что-то не так,то проверяем на ошибки
                        Log.d("MyLog", "Exception: ${task.exception}")
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            val exception =
                                task.exception as FirebaseAuthInvalidCredentialsException
                            Log.d("MyLog", "Exception 2: ${exception.errorCode}")
                            if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                                Toast.makeText(
                                    activity,
                                    FirebaseAuthConstants.ERROR_INVALID_EMAIL,
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (exception.errorCode == FirebaseAuthConstants.ERROR_WRONG_PASSWORD) {
                                Toast.makeText(
                                    activity,
                                    FirebaseAuthConstants.ERROR_WRONG_PASSWORD,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else if (task.exception is FirebaseAuthInvalidUserException) {
                            val exception =
                                task.exception as FirebaseAuthInvalidUserException
                            Log.d("MyLog", "Exception: ${exception.errorCode}")
                            if (exception.errorCode == FirebaseAuthConstants.ERROR_USER_NOT_FOUND) {
                                Toast.makeText(
                                    activity,
                                    FirebaseAuthConstants.ERROR_USER_NOT_FOUND,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
        }
    }

    // присоединяем емайл к гугл аккаунту
    private fun linkEmailToG(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        if (activity.mAuth.currentUser != null) {
            activity.mAuth.currentUser?.linkWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.link_done),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.enter_to_g),
                Toast.LENGTH_LONG
            ).show()
        }

    }

    // создаем клиента, запрашиваем токен и емайл
    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
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

    // выход из гугл аккаунта
    fun singOutGoogle() {
        getSignInClient().signOut()
    }

    // отправка данных Google account в Firebase
    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        activity.mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Sign is done", Toast.LENGTH_LONG).show()
                activity.uiUpdate(task.result.user)
            } else {
                Log.d("MyLog", "Google Sign In Exception: ${task.exception}")
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
