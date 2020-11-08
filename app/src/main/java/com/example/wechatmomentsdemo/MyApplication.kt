package com.example.wechatmomentsdemo

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * Created by Lim  on 2020/11/7.
 */
class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
    }
}