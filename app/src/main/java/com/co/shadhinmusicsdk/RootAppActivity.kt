package com.co.shadhinmusicsdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RootAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_app)

        val btnHomeFrag: Button = findViewById(R.id.btn_home_fragment)
        val btnRadio: Button = findViewById(R.id.btn_radio)

        btnHomeFrag.setOnClickListener {
            startActivity(Intent(this, AppActivity::class.java))
        }
    }
}