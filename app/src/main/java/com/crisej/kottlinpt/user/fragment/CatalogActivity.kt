package com.crisej.kottlinpt.user.fragment

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.crisej.kottlinpt.ChapterBeen
import com.crisej.kottlinpt.R
import com.crisej.kottlinpt.base.BaseActivity
import kotlinx.android.synthetic.main.activity_catalog.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.concurrent.CountDownLatch

/**
* 书的目录页面
* */
class CatalogActivity : BaseActivity() {
    var catalogRvAdapter: CatalogRvAdapter? = null
    var pageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        var intent: Intent? = intent
        pageUrl = intent?.getStringExtra("pageUrl")
        var linearLayoutManager: LinearLayoutManager? = LinearLayoutManager(this@CatalogActivity, LinearLayoutManager.VERTICAL, false)
        rv_catalog.layoutManager = linearLayoutManager
        catalogRvAdapter = CatalogRvAdapter(this@CatalogActivity, allDataLists!!)
        rv_catalog.adapter = catalogRvAdapter
        getCatalogUrl(pageUrl)
    }

    var mCurCatalogUrl: String? = null
    fun getCatalogUrl(nextPageUrl: String?){
        var latch: CountDownLatch? = CountDownLatch(1)
        var docStr: Document? = null
        var docStr1 = docStr
        Thread(Runnable {
            docStr1 = getDocumentCa(nextPageUrl)
            latch?.countDown()
        }).start()
        latch?.await()

        var catalogStr = docStr1?.getElementsByClass("chapter-control dib-wrap")
        var catalogParent = catalogStr!![0].getElementsByTag("a")
        var catalogA = catalogParent[1]
        mCurCatalogUrl = catalogA.attr("href")
        getCatalogData(mCurCatalogUrl)
    }

    var allDataLists: MutableList<ChapterBeen>? = mutableListOf()
    fun getCatalogData(catalogUrl: String?){

        var latch: CountDownLatch? = CountDownLatch(1)
        var docStr: Document? = null
        var docStr1 = docStr
        Thread(Runnable {
            docStr1 = getDocumentCa(catalogUrl)
            latch?.countDown()
        }).start()
        latch?.await()

        var volumewrap: Elements? = docStr1?.getElementsByClass("catalog-content-wrap")
        var volumeChilds: Elements? = volumewrap!![0]?.getElementsByClass("volume-wrap")
        var volumeChild: Elements? = volumeChilds!![0]?.getElementsByClass("volume")
        var contentH3: Elements? = volumeChild!![1].getElementsByTag("h3")//书籍名称信息
        var bookNameContent: String? = contentH3!![0].text()

        var bookContentUl: Elements? = volumeChild!![1].getElementsByTag("ul")

        var li: Elements? = bookContentUl!![0].getElementsByTag("li")
        for (i  in 0 until li?.size!!){
            var a: Elements? = li!![i].getElementsByTag("a")
            var chapterName = a!![0].text()
            var chapterUrl = a!![0].attr("href")
            var chapterTime = a!![0].attr("title")
            var been = ChapterBeen()
            been.chapterName = chapterName
            been.chapterUrl = chapterUrl
            been.chapterTime = chapterTime
            allDataLists?.add(been)
        }

        catalogRvAdapter?.notifyDataSetChanged()
    }

    fun getDocumentCa(pageUrl: String?): Document? {
        var document: Document? = null
        var pageUrlStr = pageUrl
        if (pageUrl?.subSequence(0, 5) != "https"){
            pageUrlStr = "https:$pageUrl"
        }
        document = Jsoup.connect(pageUrlStr)
                .userAgent("ie7：mozilla/4.0 (compatible; msie 7.0b; windows nt 6.0)")
                .timeout(5000)
                .followRedirects(false)//是否跳转
                .execute().parse()//执行
        var str: String? = document.body().text().trim().toString()
        return document
    }

    class CatalogRvAdapter(var context: BaseActivity, var dataLists: List<ChapterBeen>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return CatalogRvViewHolder(LayoutInflater.from(context).inflate(R.layout.catalog_rv_item, parent, false))
        }

        override fun getItemCount(): Int {
            if (dataLists.isEmpty()){
                return 0
            }
            return dataLists.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder: CatalogRvViewHolder? = holder as CatalogRvViewHolder
            viewHolder?.textCatalogName?.text = dataLists[position].chapterName
            viewHolder?.itemView?.setOnClickListener(View.OnClickListener {
                var intent: Intent = Intent()
                intent.putExtra("returnPageUrl", dataLists[position].chapterUrl)
                context.setResult(Activity.RESULT_OK, intent)
                context.finish()
            })
        }
    }

    class CatalogRvViewHolder: RecyclerView.ViewHolder {
        var textCatalogName: TextView? = null
        constructor(itemView: View?) : super(itemView){
            textCatalogName = itemView?.findViewById(R.id.textCatalogName)
            itemView?.setOnClickListener(View.OnClickListener {
                var intent = Intent()

            })
        }
    }





}
