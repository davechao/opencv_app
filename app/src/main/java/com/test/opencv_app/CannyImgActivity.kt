package com.test.opencv_app

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_img_canny.*

import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class CannyImgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_canny)

        val srcBitmap = BitmapFactory.decodeResource(resources, R.drawable.food)
        val srcMat = Mat()
        Utils.bitmapToMat(srcBitmap, srcMat)

        val grayMat = Mat()
        Imgproc.cvtColor(srcMat, grayMat, Imgproc.COLOR_BGR2GRAY)

        val thresholdMat = Mat()
        Imgproc.threshold(grayMat, thresholdMat, 150.0, 255.0, Imgproc.THRESH_OTSU)

        val cannyMat = Mat()
        Imgproc.Canny(thresholdMat, cannyMat, 50.0, 150.0)

        val dstBitmap = Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, srcBitmap.config)
        Utils.matToBitmap(cannyMat, dstBitmap)

        cannyImg.setImageBitmap(dstBitmap)
    }

}
