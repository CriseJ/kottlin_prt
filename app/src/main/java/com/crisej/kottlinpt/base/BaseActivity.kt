package com.crisej.kottlinpt.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

/**
 * 注释：
 * ===========================
 * Author by Jack
 * on 2018/6/20 17:33
 */
open class BaseActivity:AppCompatActivity(){

    var context: BaseActivity? = null
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        context = this


    }







}