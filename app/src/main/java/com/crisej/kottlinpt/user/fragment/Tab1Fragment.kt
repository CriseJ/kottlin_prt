package com.crisej.kottlinpt.user.fragment

import android.view.View
import com.crisej.kottlinpt.R
import com.crisej.kottlinpt.base.BaseFragment
import kotlinx.android.synthetic.main.tab1_fragment_layout.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.concurrent.CountDownLatch



class Tab1Fragment : BaseFragment() {
    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val rootView: View = inflater.inflate(R.layout.tab1_fragment_layout, null)
        getNexPageData("https://read.qidian.com/chapter/kb_MK0Oceg_LDxeCJGEX4g2/RHG071locg1Ms5iq0oQwLQ2")
        return rootView
    }

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
//        textView.text = title + "aaaaaaaaaaaaaa" + content

        var prePageUrlStr: String? = docStr1?.getElementsByClass("j_chapterPrev")?.text()
        var nextPageUrlStr: String? = docStr1?.getElementsByClass("j_chapterNext")?.text()

        //点击显示上一页数据
//        btn_prePage.setOnClickListener(View.OnClickListener { getNexPageData(prePageUrlStr) })
        //点击显示下一页数据
//        btn_nextPage.setOnClickListener(View.OnClickListener {  getNexPageData(nextPageUrlStr) })
    }


    fun getDocument(pageUrl: String?): Document? {
        var document: Document = Jsoup.connect(pageUrl)
                        .userAgent("ie7：mozilla/4.0 (compatible; msie 7.0b; windows nt 6.0)")
                        .timeout(5000)
                        .followRedirects(false)//是否跳转
                        .execute().parse()//执行
        var str: String? = document.body().text().trim().toString()
        return document
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
