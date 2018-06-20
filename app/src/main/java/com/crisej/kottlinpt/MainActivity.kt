package com.crisej.kottlinpt

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.widget.Toast
import com.crisej.kottlinpt.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :BaseActivity() {


    var exitTime: Long = 0
    val tabNormal = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    val tabSelected = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    var fragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent
        iv_a.setImageResource(R.mipmap.ic_launcher)
        tv_a.setTextColor(resources.getColor(R.color.color64C9F3))
        fragment = HomeFragment()
        this.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)//设置Fragment切换动画
                .replace(R.id.fram_layout, fragment).commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode === KeyEvent.KEYCODE_BACK && event!!.action === KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis() - exitTime > 2000){
                Toast.makeText(this@MainActivity, R.string.exit_text, Toast.LENGTH_SHORT)
            }else {
                finish()
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }






//
//    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
//            if (System.currentTimeMillis() - exitTime > 2000) {//// TODO: 2017/9/27
//                ToastUtils.LvToastView(baseContext, resources.getString(R.string.app_exit))
//                exitTime = System.currentTimeMillis()
//            } else {
//                finish()
//                System.exit(0)
//            }
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }












}
