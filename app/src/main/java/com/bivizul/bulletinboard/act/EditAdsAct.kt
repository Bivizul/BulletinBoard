package com.bivizul.bulletinboard.act

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bivizul.bulletinboard.R
import com.bivizul.bulletinboard.databinding.ActivityEditAdsBinding
import com.bivizul.bulletinboard.dialogs.DialogSpinnerHelper
import com.bivizul.bulletinboard.utils.CityHelper

class EditAdsAct : AppCompatActivity() {

    lateinit var binding: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
/*        // создаем адаптер для подключения к спиннеру
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            CityHelper.getAllCountries(this)
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // подключаем адаптер к спиннеру
        binding.spCountry.adapter = adapter*/
        onClickSelectCountry(view)
        onClickSelectCity(view)

    }

    // All OnCLick

    fun onClickSelectCountry(view: View) {
        binding.tvSelectCountry.setOnClickListener {
            // список для передачи в диалог
            val listCountry = CityHelper.getAllCountries(this)
            // запускаем диалог с переданным списком
            dialog.showSpinnerDialog(this, listCountry, binding.tvSelectCountry)
            if(binding.tvSelectCity.text.toString() != getString(R.string.select_city)){
                binding.tvSelectCity.text = getString(R.string.select_city)
            }
        }
    }

    fun onClickSelectCity(view: View) {
        binding.tvSelectCity.setOnClickListener {
            val selectedCountry = binding.tvSelectCountry.text.toString()
            if (selectedCountry != getString(R.string.select_country)) {
                // список для передачи в диалог
                val listCity = CityHelper.getAllCities(selectedCountry, this)
                // запускаем диалог с переданным списком
                dialog.showSpinnerDialog(this, listCity, binding.tvSelectCity)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.no_country_selected),
                    Toast.LENGTH_LONG
                ).show()
            }


        }
    }


}