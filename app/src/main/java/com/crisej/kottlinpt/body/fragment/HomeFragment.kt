package com.crisej.kottlinpt.body.fragment

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.crisej.kottlinpt.R
import com.crisej.kottlinpt.base.BaseFragment
import com.crisej.kottlinpt.body.been.HuaBanPicBeen
import kotlinx.android.synthetic.main.activity_navigation_draw.*
import kotlinx.android.synthetic.main.app_bar_navigation_draw.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * 注释：
 * ===========================
 * Author by Jack
 * on 2018/6/21 9:57
 */
class HomeFragment: BaseFragment(){

    var context: FragmentActivity? = null
    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        var rootView: View = LayoutInflater.from(mActivity).inflate(R.layout.home_fragment_layout, null)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = mActivity
    }

}