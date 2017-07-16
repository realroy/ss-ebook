package com.example.oldroy.ssebook.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.oldroy.ssebook.R
import com.example.oldroy.ssebook.models.User

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()
        val text_name = findViewById(R.id.text_name) as TextView?
        val button_sign_in = findViewById(R.id.button_sign_in) as Button?
        button_sign_in?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val username = text_name?.text.toString()
                val user = User(username, 100.0)
                val intent = Intent(applicationContext, StoreActivity::class.java)
                intent.putExtra("USER", user)
                startActivity(intent)
            }

        })
    }
}
