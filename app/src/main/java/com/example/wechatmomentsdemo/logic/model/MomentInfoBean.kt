package com.example.wechatmomentsdemo.logic.model

import android.text.TextUtils

/**
 * Created by Lim  on 2020/11/7.
 */
data class MomentInfoBean(val userInfo:UserInfoBean,var tweetsInfo:MutableList<TweetsInfoBean>){
    init {
        val iterator = tweetsInfo.iterator()
        while (iterator.hasNext()){
            val tweetsInfoBean = iterator.next()
            if (TextUtils.isEmpty(tweetsInfoBean.content) && tweetsInfoBean.images == null){
                iterator.remove()
            }
        }
    }
}