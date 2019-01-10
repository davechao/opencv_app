package com.test.opencv_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grayBtn.setOnClickListener {
            val intent = Intent(this, GrayImgActivity::class.java)
            startActivity(intent)
        }

        thresholdBtn.setOnClickListener {
            val intent = Intent(this, ThresholdImgActivity::class.java)
            startActivity(intent)
        }

        cannyBtn.setOnClickListener {
            val intent = Intent(this, CannyImgActivity::class.java)
            startActivity(intent)
        }

        contourBtn.setOnClickListener {
            val intent = Intent(this, ContourImgActivity::class.java)
            startActivity(intent)
        }
    }
}
