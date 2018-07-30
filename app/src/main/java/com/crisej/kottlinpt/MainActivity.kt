package com.crisej.kottlinpt

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.crisej.kottlinpt.base.BaseFragmentActivity
import com.crisej.kottlinpt.body.fragment.HomeFragment
import com.crisej.kottlinpt.user.fragment.Tab1Fragment
import com.crisej.kottlinpt.user.fragment.Tab2Fragment
import com.crisej.kottlinpt.user.fragment.Tab3Fragment
import com.crisej.kottlinpt.utils.ScreenUtil
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_navigation_draw.*
import kotlinx.android.synthetic.main.content_navigation_draw.*

class MainActivity : BaseFragmentActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    var exitTime: Long = 0
    private val tabNormal = intArrayOf(R.drawable.ic_svg_home, R.drawable.ic_svg_read, R.drawable.ic_svg_picture, R.drawable.ic_svg_personal)
    private val tabSelected = intArrayOf(R.drawable.ic_svg_home, R.drawable.ic_svg_read, R.drawable.ic_svg_picture, R.drawable.ic_svg_personal)
    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent: Intent = intent
        StatusBarUtil.setTranslucentForDrawerLayout(this@MainActivity, drawer_layout, 0)
        var statusBarHeight: Double = ScreenUtil.getStatusBarHeight0(this@MainActivity)
        toolbar.setPadding(15, statusBarHeight.toInt(), 15, 0)
        iv_a.setImageResource(R.drawable.ic_svg_home)
        tv_a.setTextColor(resources.getColor(R.color.color64C9F3))
        navDrawInit()
        fragment = HomeFragment()
//        this.supportFragmentManager
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)//设置Fragment切换动画
//                .replace(R.id.fram_layout, fragment).commit()
        replaceFragment(R.id.fram_layout, fragment as HomeFragment, "", false)?.commit()
        ll_a.setOnClickListener(this)
        ll_b.setOnClickListener(this)
        ll_c.setOnClickListener(this)
        ll_d.setOnClickListener(this)
    }

    private fun navDrawInit() {
        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
        if (keyCode == KeyEvent.KEYCODE_BACK && event!!.action == KeyEvent.ACTION_DOWN){
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
            0 -> {fragment = HomeFragment()}
            1 -> {fragment = Tab1Fragment()}
            2 -> {fragment = Tab2Fragment()}
            3 -> {fragment = Tab3Fragment()}
        }
//        this.supportFragmentManager
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)//设置Fragment切换动画
//                .replace(R.id.fram_layout, fragment).commit()
        replaceFragment(R.id.fram_layout, this!!.fragment!!, "", false)?.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_draw, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }













}
