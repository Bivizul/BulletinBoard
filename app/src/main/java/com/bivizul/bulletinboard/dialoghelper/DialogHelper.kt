package com.bivizul.bulletinboard.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.bivizul.bulletinboard.MainActivity
import com.bivizul.bulletinboard.R
import com.bivizul.bulletinboard.accounthelper.AccountHelper
import com.bivizul.bulletinboard.databinding.SignDialogBinding

class DialogHelper(private val activity: MainActivity) {

    val accHelper = AccountHelper(activity)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(activity)
        val binding = SignDialogBinding.inflate(activity.layoutInflater)
        val view = binding.root

        builder.setView(view)
        setDialogState(index, binding)

        val dialog = builder.create()

        // слушатели на кнопки
        binding.btSignUpIn.setOnClickListener {
            setOnCLickSignUpIn(index, binding, dialog)
        }
        binding.btForgetP.setOnClickListener {
            setOnCLickResetPassword(binding, dialog)
        }
        binding.btGoogleSignIn.setOnClickListener {
            accHelper.singInWithGoogle()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setOnCLickResetPassword(binding: SignDialogBinding, dialog: AlertDialog?) {
        if (binding.edSignEmail.text.isNotEmpty()) {
            activity.mAuth.sendPasswordResetEmail(binding.edSignEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            R.string.email_reset_password_was_sent,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            dialog?.dismiss()
        } else {
            binding.tvDialogMessage.visibility = View.VISIBLE
        }
    }

    private fun setOnCLickSignUpIn(index: Int, binding: SignDialogBinding, dialog: AlertDialog?) {
        // закрывает диалог после нажатия
        dialog?.dismiss()

        if (index == DialogConst.SIGN_UP_STATE) {
            // регистрация
            accHelper.signUpWithEmail(
                binding.edSignEmail.text.toString(),
                binding.edSignPassword.text.toString()
            )
        } else {
            // вход
            accHelper.signInWithEmail(
                binding.edSignEmail.text.toString(),
                binding.edSignPassword.text.toString()
            )
            Toast.makeText(activity, "Вы успешно вошли", Toast.LENGTH_LONG).show()
        }

    }

    // проверяем индекс регистрация или вход
    private fun setDialogState(index: Int, binding: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_up)
            binding.btSignUpIn.text = activity.resources.getString(R.string.sign_up_action)
            binding.btForgetP.visibility = View.GONE
        } else {
            binding.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_in)
            binding.btSignUpIn.text = activity.resources.getString(R.string.sign_in_action)
            binding.btForgetP.visibility = View.VISIBLE
        }
    }
}