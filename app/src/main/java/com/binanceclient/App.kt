package com.binanceclient

import android.app.Application
import android.content.res.Resources


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        res = resources
    }

    companion object {
        private var res: Resources? = null
        val resourses: Resources? get() = res
    }
}