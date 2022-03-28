package com.bivizul.bulletinboard

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bivizul.bulletinboard.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // lateinit - инициализировать после
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.mainContent.toolbar,
            R.string.open,
            R.string.close
        )
        // открытие по нажатию
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // передача событий из NavigationView по нажатию в onNavigationItemSelected
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ads -> {
                Toast.makeText(this, "Presed id_my_ads", Toast.LENGTH_LONG).show()
            }
            R.id.id_car -> {
                Toast.makeText(this, "Presed id_car", Toast.LENGTH_LONG).show()
            }
            R.id.id_pc -> {
                Toast.makeText(this, "Presed id_pc", Toast.LENGTH_LONG).show()
            }
            R.id.id_smart -> {
                Toast.makeText(this, "Presed id_smart", Toast.LENGTH_LONG).show()
            }
            R.id.id_ha -> {
                Toast.makeText(this, "Presed id_ha", Toast.LENGTH_LONG).show()
            }
            R.id.id_sign_up -> {
                Toast.makeText(this, "Presed id_sign_up", Toast.LENGTH_LONG).show()
            }
            R.id.id_sign_in -> {
                Toast.makeText(this, "Presed id_sign_in", Toast.LENGTH_LONG).show()
            }
            R.id.id_sign_out -> {
                Toast.makeText(this, "Presed id_sign_out", Toast.LENGTH_LONG).show()
            }

        }

        // после нажатий возвращем drawer на место
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}