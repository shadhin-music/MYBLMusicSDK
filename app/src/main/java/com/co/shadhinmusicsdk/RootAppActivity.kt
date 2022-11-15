package com.co.shadhinmusicsdk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RootAppActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_app)

        val etMobileNumber: EditText = findViewById(R.id.et_mobile_number)
        val btnHomeFrag: Button = findViewById(R.id.btn_home_fragment)
        val mobileNm = etMobileNumber.text
        btnHomeFrag.setOnClickListener {
            if (mobileNm.isNotEmpty()) {
                startActivity(Intent(this, AppActivity::class.java)
                    .apply {
                        putExtra("mobile_number", "")
                    })
            } else {
                Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show()
            }
        }
    }
}