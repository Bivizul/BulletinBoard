package com.bivizul.bulletinboard.act

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.bivizul.bulletinboard.databinding.ActivityEditAdsBinding
import com.bivizul.bulletinboard.utils.CityHelper

class EditAdsAct : AppCompatActivity() {

    private lateinit var binding: ActivityEditAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // создаем адаптер для подключения к спиннеру
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            CityHelper.getAllCountries(this)
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // подключаем адаптер к спиннеру
        binding.spCountry.adapter = adapter
    }
}