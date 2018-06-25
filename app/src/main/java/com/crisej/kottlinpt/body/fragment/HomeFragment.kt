package com.crisej.kottlinpt.body.fragment

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.crisej.kottlinpt.R
import com.crisej.kottlinpt.base.BaseFragment
import com.crisej.kottlinpt.body.been.HuaBanPicBeen
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

    private var homeRvAdapter: HomeFgRvAdapter? = null
    private var listData: MutableList<Int>? = mutableListOf()
    private var mRandomDataList: MutableList<Int>? = mutableListOf()
    private var picBeenDataList: MutableList<HuaBanPicBeen>? = mutableListOf()

    var context: FragmentActivity? = null
    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        var rootView: View = LayoutInflater.from(mActivity).inflate(R.layout.home_fragment_layout, null)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = mActivity
        getHbNetPicData(huaBanNetHostUrl)
        var sgManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        ryView_home_fragment?.layoutManager = sgManager
        homeRvAdapter = HomeFgRvAdapter(this!!.context!!, mRandomDataList!!, this!!.picBeenDataList!!)
        ryView_home_fragment?.adapter = homeRvAdapter
    }

    var huaBanNetHostUrl: String? = "http://huaban.com"
    fun getHbNetPicData(pageUrl: String?){
        var latch: CountDownLatch ? = CountDownLatch(1)
        var docStr: Document? = null
        var docStr1 = docStr
        Thread(Runnable {
            docStr1 = getHbNetPicDocument(pageUrl)
            latch?.countDown()
        }).start()
        latch?.await()

        var m: Element? = docStr1?.getElementById("page")
        var recommendContainer: Elements? = m?.getElementsByTag("div")
        var divs = recommendContainer!![0]?.getElementsByTag("div")
        var recommendContainerFromId: Elements? = divs!![0]?.getElementsByTag("div")
        var divClasses: Elements? = recommendContainerFromId!![3]?.getElementsByTag("div")
        for (i in 0 until divClasses?.size!!){
            var recommendImgbox: Elements? = divClasses[i].getElementsByClass("recommend-imgbox recommend-box")
            for (j in 0 until recommendImgbox?.size!!){
                var a = recommendImgbox[i].getElementsByTag("a")
                var morePicPageChildUrl: String? = a[0].attr("href")
                var curImgUrl: String? = a[0].childNode(0).attr("src")
                var huaBanPicBeen = HuaBanPicBeen()
                huaBanPicBeen.morePicPageChildUrl = morePicPageChildUrl
                huaBanPicBeen.curImgUrl = curImgUrl
                picBeenDataList?.add(huaBanPicBeen)
            }
        }
        for (i in 0 until picBeenDataList?.size!!){
            mRandomDataList?.add(Random().nextInt(300) + 300)
        }
        homeRvAdapter?.notifyDataSetChanged()
    }

    fun getHbNetPicDocument(pageUrl: String?): Document? {
        var document: Document? = null
        var pageUrlStr = pageUrl
        document = Jsoup.connect(pageUrlStr)
                .userAgent("ie7：mozilla/4.0 (compatible; msie 7.0b; windows nt 6.0)")
                .timeout(5000)
                .followRedirects(false)//是否跳转
                .execute().parse()//执行
        var str: String? = document.body().text().trim().toString()
        return document
    }

    class HomeFgRvAdapter(var context: FragmentActivity,var randomDataList: List<Int>, var data: List<HuaBanPicBeen>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return HomeFgRyViewHolder(LayoutInflater.from(context).inflate(R.layout.home_rv_item, parent, false))
        }

        override fun getItemCount(): Int {
            if (data.isEmpty()){
                return 0
            }
            return data.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            //这里需要用as 强转成HomeFgRyViewHolder
            var viewHolder: HomeFgRyViewHolder? = holder as HomeFgRyViewHolder
            var viewGroupParam: ViewGroup.LayoutParams? = viewHolder?.iv?.layoutParams
            viewGroupParam?.height = randomDataList[position]
            viewHolder?.iv?.layoutParams = viewGroupParam
            Glide.with(context)
                    .load("http:$data[position].curImgUrl")
                    .into(viewHolder?.iv!!)
        }
    }

   class HomeFgRyViewHolder: RecyclerView.ViewHolder{
        var iv: ImageView? = null
        constructor(itemView: View?) : super(itemView){
            iv = itemView?.findViewById(R.id.iv_home_rv_fg)
        }
    }


}