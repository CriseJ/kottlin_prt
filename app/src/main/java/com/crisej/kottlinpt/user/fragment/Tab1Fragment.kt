package com.crisej.kottlinpt.user.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ScrollView
import com.crisej.kottlinpt.R
import com.crisej.kottlinpt.base.BaseFragment
import kotlinx.android.synthetic.main.tab1_fragment_layout.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.concurrent.CountDownLatch



class Tab1Fragment : BaseFragment(), View.OnClickListener{
    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView: View = LayoutInflater.from(mActivity).inflate(R.layout.tab1_fragment_layout, null)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_prePage.setOnClickListener(this)
        btn_nextPage.setOnClickListener(this)
        btn_catalog.setOnClickListener(this)
        getNexPageData("https://read.qidian.com/chapter/kb_MK0Oceg_LDxeCJGEX4g2/RHG071locg1Ms5iq0oQwLQ2")
    }

    override fun onClick(view: View?) {
       when(view?.id){
           R.id.btn_prePage -> getNexPageData(mPrePageUrl)  //点击显示上一页数据
           R.id.btn_nextPage -> getNexPageData(mNextPageUrl)  //点击显示下一页数据
           R.id.btn_catalog ->  goToCatalogActivity()//点击显示该书籍的目录
       }
    }

    private val REQ_CATALOG: Int? = 1001
    fun goToCatalogActivity(){
        var intent = Intent(mActivity, CatalogActivity::class.java)
        intent.putExtra("pageUrl", mCurPageUrl)
        if (REQ_CATALOG != null) {
            mActivity?.startActivityForResult(intent, REQ_CATALOG)
        }
    }

    var mPrePageUrl: String? = null
    var mNextPageUrl: String? = null
    var mCurPageUrl: String? = null

    private fun getNexPageData(nextPageUrl: String?) {

        var latch: CountDownLatch ? = CountDownLatch(1)
        var docStr: Document? = null
        var docStr1 = docStr
        Thread(Runnable {
            docStr1 = getDocument(nextPageUrl)
            latch?.countDown()
        }).start()
        latch?.await()
        var articalElement0: Elements? = docStr1?.getElementsByClass("j_chapterName")
        var articalElement1: Elements? = docStr1?.getElementsByClass("read-content j_readContent")
        var title: String? = articalElement0!![0]?.text()
        var content: String = articalElement1!![0].text()

        var prePageUrlStr: String? = docStr1?.getElementById("j_chapterPrev")?.attr("href")
        var nextPageUrlStr: String? = docStr1?.getElementById("j_chapterNext")?.attr("href")
        mPrePageUrl = prePageUrlStr
        mNextPageUrl = nextPageUrlStr
        tvTitle.text = title
        tvContent.text = content
        sv_content.post( Runnable { sv_content.fullScroll(ScrollView.FOCUS_UP) })
    }

    fun getDocument(pageUrl: String?): Document? {
        var document: Document? = null
        var pageUrlStr = pageUrl
        mCurPageUrl = pageUrlStr
        if (pageUrl?.subSequence(0, 5) != "https"){
            pageUrlStr = "https:$pageUrl"
            mCurPageUrl = pageUrlStr
        }
        document = Jsoup.connect(pageUrlStr)
                .userAgent("ie7：mozilla/4.0 (compatible; msie 7.0b; windows nt 6.0)")
                .timeout(5000)
                .followRedirects(false)//是否跳转
                .execute().parse()//执行
        var str: String? = document.body().text().trim().toString()
        return document
    }

    fun reqCatalog(data: Intent?){
        var pageUrl: String? = data?.getStringExtra("returnPageUrl")
        getNexPageData(pageUrl)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK){
            return
        }
        when(requestCode){
            REQ_CATALOG -> reqCatalog(data)
        }
    }

//    fun getNextUrl(): String?{
//        var pageUrl: String? = null
//        var webClient: WebClient? = WebClient()
//        webClient?.options?.isCssEnabled = false
//        webClient?.options?.isJavaScriptEnabled = false
//        //去拿网页
//        var htmlPage: HtmlPage? = webClient?.getPage("")
//        //得到表单
//        var button: HtmlButton? = htmlPage?.getElementsById()
//
//
//
//
//
//
//    }







}
