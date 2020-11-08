package com.example.wechatmomentsdemo.logic.network.api

import com.example.wechatmomentsdemo.logic.model.TweetsInfoBean
import com.example.wechatmomentsdemo.logic.model.UserInfoBean
import retrofit2.http.*

/**
 *Created by Lim  on 2020/11/7.
 */
interface UserService {

    /**
     * 获取用户信息
     */
    @GET("/user/{name}")
    suspend fun getUserInfo(@Path("name") name: String): UserInfoBean

    /**
     * 获取用户的tweets信息
     */
    @GET("/user/{name}/tweets")
    suspend fun getTweetsByUserName(@Path("name") name: String): MutableList<TweetsInfoBean>

    companion object {

    }

}