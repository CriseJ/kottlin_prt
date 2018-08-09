package com.crisej.kottlinpt.body.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.crisej.kottlinpt.R
import com.crisej.kottlinpt.base.BaseActivity
import com.crisej.kottlinpt.mapp.ActivityIntentUtils
import kotlinx.android.synthetic.main.activity_to_do_list.*

class ToDoListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()

            ActivityIntentUtils.startWritBacklogActivity(this@ToDoListActivity)
        }
    }


}
