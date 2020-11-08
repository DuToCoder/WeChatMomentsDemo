package com.example.wechatmomentsdemo.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Lim  on 2020/11/7.
 */
data class UserInfoBean(val avatar:String,val nick:String,val username:String){
    @SerializedName("profile-image")
    lateinit var profileImage:String
}