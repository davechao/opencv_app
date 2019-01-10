package com.test.opencv_app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class MainActivity : AppCompatActivity() {

    var progress: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val srcBitmap = BitmapFactory.decodeResource(resources, R.drawable.food)
        imageView.setImageBitmap(srcBitmap)

        val srcMat = Mat()
        Utils.bitmapToMat(srcBitmap, srcMat)

        val grayMat = Mat()
        Imgproc.cvtColor(srcMat, grayMat, Imgproc.COLOR_BGR2GRAY)

        val thresholdMat = Mat()
        Imgproc.threshold(grayMat, thresholdMat, 150.0, 255.0, Imgproc.THRESH_OTSU)

        val cannyMat = Mat()
        Imgproc.Canny(thresholdMat, cannyMat, 50.0, 150.0)

        val dstBitmap = Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, srcBitmap.config)

        grayBtn.setOnClickListener {
            Utils.matToBitmap(grayMat, dstBitmap)
            imageView.setImageBitmap(dstBitmap)
        }

        thresholdBtn.setOnClickListener {
            Utils.matToBitmap(thresholdMat, dstBitmap)
            imageView.setImageBitmap(dstBitmap)
        }

        cannyBtn.setOnClickListener {
            Utils.matToBitmap(cannyMat, dstBitmap)
            imageView.setImageBitmap(dstBitmap)
        }

        contourBtn.setOnClickListener {
            val contourMat = srcMat.clone()
            val contours = mutableListOf<MatOfPoint>()
            Imgproc.findContours(cannyMat, contours, Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE)
            if (contours.isEmpty()) return@setOnClickListener

            showProgressBar(this)
            Completable.fromAction {
                contours.forEachIndexed { index, _ ->
                    Imgproc.drawContours(contourMat, contours, index, Scalar(0.0, 0.0, 255.0), 3)
                }
                Utils.matToBitmap(contourMat, dstBitmap)
                imageView.setImageBitmap(dstBitmap)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribeBy(
                onComplete = { closeProgressBar() },
                onError = { it.printStackTrace() }
            )
        }
    }

    private fun showProgressBar(context: Context) {
        progress = MaterialDialog.Builder(context)
            .content(R.string.hint_please_wait)
            .contentColorRes(R.color.colorAccent)
            .theme(Theme.LIGHT)
            .progress(true, 0)
            .cancelable(false)
            .show()
    }

    private fun closeProgressBar() {
        progress?.dismiss()
    }
}
