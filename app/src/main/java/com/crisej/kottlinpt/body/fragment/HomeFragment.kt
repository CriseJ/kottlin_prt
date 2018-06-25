package com.crisej.kottlinpt.body.fragment

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.crisej.kottlinpt.R
import com.crisej.kottlinpt.base.BaseFragment
import kotlinx.android.synthetic.main.home_fragment_layout.*

/**
 * 注释：
 * ===========================
 * Author by Jack
 * on 2018/6/21 9:57
 */
class HomeFragment: BaseFragment(){

//    private var homeRvAdapter: HomeFgRvAdapter? = null
    private var listData: MutableList<Int>? = mutableListOf()
    var context: FragmentActivity? = null
    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        var rootView: View = LayoutInflater.from(activity).inflate(R.layout.home_fragment_layout, null)
        context = activity
        getData()
        var sgManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        ryView_home_fragment?.layoutManager = sgManager
        var homeRvAdapter: HomeFgRvAdapter? = HomeFgRvAdapter(this!!.context!!,listData!!)
        ryView_home_fragment?.adapter = homeRvAdapter
        homeRvAdapter?.notifyDataSetChanged()
        return rootView
    }

    private fun getData(){
        for (i in 0 until 50){
            listData!!.add(i)
        }
    }

    class HomeFgRvAdapter(var context: FragmentActivity,var data: List<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return HomeFgRyViewHolder(LayoutInflater.from(context).inflate(R.layout.home_rv_item, parent, false))
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            //这里需要用as 强转成HomeFgRyViewHolder
            var viewHolder: HomeFgRyViewHolder? = holder as HomeFgRyViewHolder
            viewHolder?.iv?.setImageResource(R.mipmap.ic_launcher)
        }
    }

   class HomeFgRyViewHolder: RecyclerView.ViewHolder{
        var iv: ImageView? = null
        constructor(itemView: View?) : super(itemView){
            iv = itemView?.findViewById(R.id.iv_home_rv_fg)
        }
    }


}