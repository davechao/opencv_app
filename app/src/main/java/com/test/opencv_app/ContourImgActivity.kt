package com.test.opencv_app

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_img_contour.*

import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class ContourImgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_contour)

        val srcBitmap = BitmapFactory.decodeResource(resources, R.drawable.food)
        val srcMat = Mat()
        Utils.bitmapToMat(srcBitmap, srcMat)

        val grayMat = Mat()
        Imgproc.cvtColor(srcMat, grayMat, Imgproc.COLOR_BGR2GRAY)

        val thresholdMat = Mat()
        Imgproc.threshold(grayMat, thresholdMat, 150.0, 255.0, Imgproc.THRESH_OTSU)

        val cannyMat = Mat()
        Imgproc.Canny(thresholdMat, cannyMat, 50.0, 150.0)

        val contourMat = srcMat.clone()
        val contours = mutableListOf<MatOfPoint>()
        Imgproc.findContours(cannyMat, contours, Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
        if(contours.isEmpty()) return
        contours.forEachIndexed { index, _ ->
            Imgproc.drawContours(contourMat, contours, index, Scalar(0.0, 0.0 , 255.0), 3)
        }
        
        val dstBitmap = Bitmap.createBitmap(srcBitmap!!.width, srcBitmap.height, srcBitmap.config)
        Utils.matToBitmap(contourMat, dstBitmap)
        contourImg.setImageBitmap(dstBitmap)
    }

}
