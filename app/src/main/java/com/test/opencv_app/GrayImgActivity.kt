package com.test.opencv_app

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_img_gray.*
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class GrayImgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_gray)

        val srcBitmap = BitmapFactory.decodeResource(resources, R.drawable.food)
        val srcMat = Mat()
        Utils.bitmapToMat(srcBitmap, srcMat)

        val dstMat = Mat()
        Imgproc.cvtColor(srcMat, dstMat, Imgproc.COLOR_BGR2GRAY)

        val dstBitmap = Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, srcBitmap.config)
        Utils.matToBitmap(dstMat, dstBitmap)

        grayImg.setImageBitmap(dstBitmap)
    }

}
