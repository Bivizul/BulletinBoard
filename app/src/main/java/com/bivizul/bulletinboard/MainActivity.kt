package com.bivizul.bulletinboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bivizul.bulletinboard.act.EditAdsActivity
import com.bivizul.bulletinboard.databinding.ActivityMainBinding
import com.bivizul.bulletinboard.dialoghelper.DialogConst
import com.bivizul.bulletinboard.dialoghelper.DialogHelper
import com.bivizul.bulletinboard.dialoghelper.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // lateinit - инициализировать после
    private lateinit var tvAccount: TextView
    private lateinit var binding: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    // Прослушиваем нажатия на элементы из меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.id_new_ads){
            val intent = Intent(this, EditAdsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    // Добавляем меню NEW на тулбар
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // получаем результат запроса входа через Google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE) {
            //Log.d("MyLog", "Sign In result")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    Log.d("MyLog", "Api error: don't 0")
                    dialogHelper.accHelper.signInFirebaseWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("MyLog", "Api error: ${e.message}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    private fun init() {
        // Указываем тулбар по умолчанию
        setSupportActionBar(binding.mainContent.toolbar)

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
        tvAccount = binding.navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)
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
                dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
            }
            R.id.id_sign_in -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
            }
            R.id.id_sign_out -> {
                uiUpdate(null)
                mAuth.signOut()
                dialogHelper.accHelper.singOutGoogle()
                Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_LONG).show()
            }

        }

        // после нажатий возвращем drawer на место
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    fun uiUpdate(user: FirebaseUser?) {
        tvAccount.text = if (user == null) {
            resources.getString(R.string.not_reg)
        } else {
            user.email
        }
    }
}