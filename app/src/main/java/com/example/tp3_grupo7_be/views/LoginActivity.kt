package com.example.tp3_grupo7_be.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tp3_grupo7_be.R


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val usuarioLogueado = sharedPreferences.getBoolean("usuarioLogueado", false)
        val intent = Intent(this, MainActivity::class.java)


        if(usuarioLogueado) {
            startActivity(intent)
            finish()
        }


    }


}