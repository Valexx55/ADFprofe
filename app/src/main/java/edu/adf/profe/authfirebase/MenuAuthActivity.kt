package edu.adf.profe.authfirebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.R

class MenuAuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun nuevaCuenta(view: View) {
        val intentNueva = Intent(this, RegistroActivity::class.java)
        startActivity(intentNueva)
        finish()
    }
    fun acceder(view: View) {
        val intentAuth = Intent(this, AuthenticationActivity::class.java)
        startActivity(intentAuth)
        finish()

    }
}