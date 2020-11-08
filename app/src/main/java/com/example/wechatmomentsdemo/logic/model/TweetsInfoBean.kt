package com.example.wechatmomentsdemo.logic.model

/**
 * Created by Lim  on 2020/11/7.
 */
data class TweetsInfoBean(val content:String?,var images:List<Image>?,val sender:Sender
                          ,var comments:List<Comment>?) {

    data class Image(var url:String)

    data class Sender(val username:String,val nick:String,val avatar:String)

    data class Comment(val content: String,val sender:Sender)
}