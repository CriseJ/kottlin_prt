package com.crisej.kottlinpt.user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import com.crisej.kottlinpt.MainActivity
import com.crisej.kottlinpt.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

     var spRvAdapter: SplashRvAdapter? = null
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        iv_splash.setImageResource(R.mipmap.ic_launcher)
        runOnUiThread(Runnable { kotlin.run {
            iv_splash.animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale)
            iv_splash.animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate)
            iv_splash.visibility = View.GONE
        } })

        ryView_splash.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val scrollHelper = PagerSnapHelper()
        scrollHelper.attachToRecyclerView(ryView_splash)
        var data: MutableList<Int>? = mutableListOf()
        for (i in 0 until 2){
            data!!.add(R.mipmap.ic_launcher)
        }
        spRvAdapter = SplashRvAdapter(this, data!!)
        ryView_splash.adapter = spRvAdapter
        ryView_splash.setScrollingTouchSlop(3)
    }

     class SplashRvAdapter(var context: AppCompatActivity?,val data: List<Int>): Adapter<RecyclerView.ViewHolder>() {
         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
             return SplashRvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.splash_rv_item, parent, false))
         }

         override fun getItemCount(): Int {
             return data.size
         }

         override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder: SplashRvViewHolder = holder as SplashRvViewHolder
             viewHolder.iv.setImageResource(R.mipmap.ic_launcher)
             if (position === data.size - 1){
                viewHolder.btn_into.visibility = View.VISIBLE
                 viewHolder.btn_into.setOnClickListener{
                     var activity: SplashActivity = context as SplashActivity
                     activity.goToActivity()
                 }
             }else {
                 viewHolder.btn_into.visibility = View.GONE
             }
         }
     }

    class SplashRvViewHolder: RecyclerView.ViewHolder{
        var iv: ImageView
        var btn_into: Button
        constructor(itemView: View?) : super(itemView){
            iv = itemView!!.findViewById(R.id.iv_rv_splash)
            btn_into = itemView!!.findViewById(R.id.btn_splash_into)
        }
    }

    fun goToActivity(){
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
