package com.bivizul.bulletinboard.act

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bivizul.bulletinboard.R
import com.bivizul.bulletinboard.databinding.ActivityEditAdsBinding
import com.bivizul.bulletinboard.dialogs.DialogSpinnerHelper
import com.bivizul.bulletinboard.fragments.CloseInterfaceFragment
import com.bivizul.bulletinboard.fragments.ImageListFragment
import com.bivizul.bulletinboard.utils.CityHelper
import com.bivizul.bulletinboard.utils.ImagePicker
import com.bivizul.bulletinboard.utils.ImagePicker.options
import io.ak1.pix.helpers.PixBus
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment

class EditAdsActivity : AppCompatActivity(),CloseInterfaceFragment {

    lateinit var binding: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()
//    private val pixFragment = pixFragment(options)

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
        onClickGetImages(view)

    }

    // При закрытии фрагмента покажем scrollViewMain
    override fun onCloseFragment() {
        binding.scrollViewMain.visibility = View.VISIBLE
    }

    // ???
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        pixFragment(options){
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> PixBus.onBackPressedEvent()
                PixEventCallback.Status.BACK_PRESSED -> super.onBackPressed()

            }
        }
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

    fun onClickGetImages(view: View) {
        binding.ibAddPhoto.setOnClickListener {
            // Прячем scrollViewMain
            binding.scrollViewMain.visibility = View.GONE
            // Создаем ФМ
            val fm = supportFragmentManager.beginTransaction()
            // Заменяем конетейнер place_holder на фрагмент ImageListFragment
            fm.replace(R.id.place_holder,ImageListFragment(this))
            // Применяем изменения
            fm.commit()

            /*addPixToActivity(R.id.place_holder, options) {
                when (it.status) {
                    PixEventCallback.Status.SUCCESS -> PixBus.onBackPressedEvent()
                    PixEventCallback.Status.BACK_PRESSED -> super.onBackPressed()
                }
            }*/

        }

    }


}