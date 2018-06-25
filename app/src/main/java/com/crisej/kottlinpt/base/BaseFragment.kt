package com.crisej.kottlinpt.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crisej.kottlinpt.R

/**
 * 注释：
 * ===========================
 * Author by Jack
 * on 2018/6/20 19:18
 */
open class BaseFragment: Fragment() {
    protected var mIsLoadedData = false
    var mActivity: BaseFragmentActivity? = null
    var mInflater: LayoutInflater? = null
    var mFManager: FragmentManager? = null
    var mBundle: Bundle? = null
    var mFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mActivity = activity as BaseFragmentActivity?
        this.mFManager = mActivity?.mFManager
        this.mBundle = arguments
        this.mFragment = this
    }


    fun setContentView(inflater: LayoutInflater, layoutResID: Int, container: ViewGroup): View {
        return setContentView(inflater, layoutResID, container, false)
    }

    fun setContentView(inflater: LayoutInflater, layoutResID: Int, container: ViewGroup, attachToRoot: Boolean): View {
        this.mInflater = inflater
        return inflater.inflate(layoutResID, container, attachToRoot)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
//        Logger.e("BaseFragment：：：setUserVisibleHint")
        if (isResumed) {
            handleOnVisibilityChangedToUser(isVisibleToUser)
        }
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            handleOnVisibilityChangedToUser(true)
        }
        val name = javaClass.simpleName
//        Logger.e("$name:::当前fragment名称")
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            handleOnVisibilityChangedToUser(false)
        }
        val name = javaClass.simpleName
    }

    /**
     * 处理对用户是否可见
     *
     * @param isVisibleToUser
     */
    private fun handleOnVisibilityChangedToUser(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            // 对用户可见
            if (!mIsLoadedData) {
                mIsLoadedData = true
                onLazyLoadOnce()
            }
            onVisibleToUser()
        } else {
            // 对用户不可见
            onInvisibleToUser()
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected fun onLazyLoadOnce() {}

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    protected fun onVisibleToUser() {}

    /**
     * 对用户不可见时触发该方法
     */
    protected fun onInvisibleToUser() {}

    // Intent的页面跳转
    fun gotoActivity(clazz: Class<out Activity>, finish: Boolean) {
        val intent = Intent(mActivity, clazz)
        startActivity(intent)
        if (finish) {
            activity!!.finish()
        }
        activity!!.overridePendingTransition(R.anim.lv_push_right_in, R.anim.lv_push_right_out)
    }

    fun gotoActivity(clazz: Class<out Activity>, bundle: Bundle?, finish: Boolean) {
        val intent = Intent(mActivity, clazz)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
        if (finish) {
            activity!!.finish()
        }
        activity!!.overridePendingTransition(R.anim.lv_push_right_in, R.anim.lv_push_right_out)
    }

    fun gotoActivity(clazz: Class<out Activity>, bundle: Bundle?, flags: Int, finish: Boolean) {
        val intent = Intent(mActivity, clazz)
        if (bundle != null) intent.putExtras(bundle)
        intent.addFlags(flags)
        startActivity(intent)
        if (finish) {
            activity!!.finish()
        }
        activity!!.overridePendingTransition(R.anim.lv_push_right_in, R.anim.lv_push_right_out)

    }

}