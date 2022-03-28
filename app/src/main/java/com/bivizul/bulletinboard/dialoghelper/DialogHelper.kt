package com.bivizul.bulletinboard.dialoghelper

import android.app.AlertDialog
import android.view.View
import com.bivizul.bulletinboard.MainActivity
import com.bivizul.bulletinboard.R
import com.bivizul.bulletinboard.accounthelper.AccountHelper
import com.bivizul.bulletinboard.databinding.SignDialogBinding

class DialogHelper(private val activity: MainActivity) {

    private val accHelper = AccountHelper(activity)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(activity)
        val binding = SignDialogBinding.inflate(activity.layoutInflater)
        val view = binding.root

        if (index == DialogConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_up)
            binding.btSignUpIn.text = activity.resources.getString(R.string.sign_up_action)
            binding.btForgetP.visibility = View.GONE
        } else {
            binding.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_in)
            binding.btSignUpIn.text = activity.resources.getString(R.string.sign_in_action)
            binding.btForgetP.visibility = View.VISIBLE
        }

        binding.btSignUpIn.setOnClickListener {
            if (index == DialogConst.SIGN_UP_STATE) {
                accHelper.signUpWithEmail(
                    binding.edSignEmail.text.toString(),
                    binding.edSignPassword.text.toString()
                )
            } else {
                TODO()
            }
        }

        builder.setView(view)
        builder.show()
    }
}