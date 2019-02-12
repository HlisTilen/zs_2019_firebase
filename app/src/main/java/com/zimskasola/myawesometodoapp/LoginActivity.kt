package com.zimskasola.myawesometodoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            openMainScreen()
        }

        registerButton.setOnClickListener {

        }
    }

    private fun openMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
