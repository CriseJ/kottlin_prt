package com.crisej.kottlinpt.mapp

import android.app.Application
import android.content.Context

/**
 * 注释：
 * ===========================
 * Author by Jack
 * on 2018/6/20 15:09
 */
class MainApplication: Application() {
    var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        this.context = applicationContext
    }




}