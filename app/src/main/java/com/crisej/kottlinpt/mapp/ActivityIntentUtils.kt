package com.crisej.kottlinpt.mapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.crisej.kottlinpt.body.ui.BacklogActivity
import com.crisej.kottlinpt.body.ui.ToDoListActivity

open class ActivityIntentUtils {

    companion object {
        /**
         * 启动待办清单Activity
         * @param context
         * */
        open fun startToDoListActivity(context: Context){
            var intent: Intent?= Intent(context, ToDoListActivity::class.java)
            context.startActivity(intent)
        }
        /**
         * 启动待办事项Activity
         * @param context
         * */
        open fun startWritBacklogActivity(context: Context){
            var intent: Intent?= Intent(context, BacklogActivity::class.java)
            context.startActivity(intent)
        }







    }
}