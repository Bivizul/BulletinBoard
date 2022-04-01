package com.bivizul.bulletinboard.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bivizul.bulletinboard.R
import com.bivizul.bulletinboard.databinding.ActivityEditAdsBinding

class EditAdsAct : AppCompatActivity() {

    private lateinit var binding: ActivityEditAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}