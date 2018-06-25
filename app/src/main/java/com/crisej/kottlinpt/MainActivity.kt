package com.crisej.kottlinpt

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.crisej.kottlinpt.base.BaseFragmentActivity
import com.crisej.kottlinpt.body.fragment.HomeFragment
import com.crisej.kottlinpt.user.fragment.Tab1Fragment
import com.crisej.kottlinpt.user.fragment.Tab2Fragment
import com.crisej.kottlinpt.user.fragment.Tab3Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseFragmentActivity(), View.OnClickListener {
    var exitTime: Long = 0
    private val tabNormal = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val tabSelected = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent: Intent = intent
        iv_a.setImageResource(R.mipmap.ic_launcher)
        tv_a.setTextColor(resources.getColor(R.color.color64C9F3))
        fragment = HomeFragment()
        this.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)//设置Fragment切换动画
                .replace(R.id.fram_layout, fragment).commit()
        ll_a.setOnClickListener(this)
        ll_b.setOnClickListener(this)
        ll_c.setOnClickListener(this)
        ll_d.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
      when(view!!.id){
          R.id.ll_a -> switchTab(0)
          R.id.ll_b -> switchTab(1)
          R.id.ll_c -> switchTab(2)
          R.id.ll_d -> switchTab(3)
        }
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

    private var preTabIv: ImageView? = null
    private var preTabTv: TextView? = null
    private var preIndex: Int = 0
    fun switchTab(index: Int) {
        if (preIndex == index) {
            return
        }
        val prell = ll_tabBottom.getChildAt(preIndex) as LinearLayout
        val preiv = prell.getChildAt(0) as ImageView
        preiv.setImageResource(tabNormal[preIndex])
        val pretv = prell.getChildAt(1) as TextView
        pretv.setTextColor(resources.getColor(R.color.colorCCCCCC))

        val ll = ll_tabBottom.getChildAt(index) as LinearLayout
        val iv = ll.getChildAt(0) as ImageView
        iv.setImageResource(tabSelected[index])
        val tv = ll.getChildAt(1) as TextView
        tv.setTextColor(this.resources.getColor(R.color.color64C9F3))
        preTabIv = iv
        preTabTv = tv
        preIndex = index

        when (index) {
            0 -> fragment = HomeFragment()
            1 -> fragment = Tab1Fragment()
            2 -> fragment = Tab2Fragment()
            3 -> fragment = Tab3Fragment()
        }
        this.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)//设置Fragment切换动画
                .replace(R.id.fram_layout, fragment).commit()
    }
}
