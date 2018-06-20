package com.crisej.kottlinpt.widget

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnFlingListener
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.View

/**
 * 注释：
 * ===========================
 * Author by Jack
 * on 2018/6/20 13:29
 */
class PgaerScrollHelper: OnFlingListener {
    override fun onFling(velocityX: Int, velocityY: Int): Boolean {
        return false
    }

    var rv: RecyclerView? = null
    constructor(rv: RecyclerView){
        this.rv = rv
    }

    fun scrollRv(){
        rv?.onFlingListener = this
    }

















}