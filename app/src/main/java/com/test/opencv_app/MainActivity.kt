package com.test.opencv_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("opencv_java3")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grayBtn.setOnClickListener {
            val intent = Intent(this, GrayImgActivity::class.java)
            startActivity(intent)
        }

        cannyBtn.setOnClickListener {
            val intent = Intent(this, CannyImgActivity::class.java)
            startActivity(intent)
        }

    }
}
