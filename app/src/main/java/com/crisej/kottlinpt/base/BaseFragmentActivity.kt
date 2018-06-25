package com.crisej.kottlinpt.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * 注释：
 * ===========================
 * Author by Jack
 * on 2018/6/25 10:56
 */
open class BaseFragmentActivity : BaseActivity() {

    var mFManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFManager = supportFragmentManager
    }

    /**
     * 添加Fragment
     * @param isAddBackStack 是否添加回退栈
     */
    fun addFragment(contentId: Int, fragment: Fragment, tag: String, isAddBackStack: Boolean): FragmentTransaction? {
        val transaction = mFManager?.beginTransaction()
        transaction?.add(contentId, fragment, tag)
        if (isAddBackStack) {
            transaction?.addToBackStack(tag)
        }
        return transaction
    }

    /**
     * 覆盖Fragment
     * @param isAddBackStack 是否添加回退栈
     */
    fun replaceFragment(contentId: Int, fragment: Fragment, tag: String, isAddBackStack: Boolean): FragmentTransaction? {
        val transaction = mFManager?.beginTransaction()
        transaction?.replace(contentId, fragment, tag)
        if (isAddBackStack) {
            transaction?.addToBackStack(tag)
        }
        return transaction
    }


}
