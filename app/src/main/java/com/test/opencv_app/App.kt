package com.test.opencv_app

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("opencv_java3")
    }
}