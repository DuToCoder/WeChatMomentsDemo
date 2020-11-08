package com.example.wechatmomentsdemo.logic.network

import com.example.wechatmomentsdemo.logic.network.api.UserService

/**
 * 管理所有网络请求。
 *
 */
class AppNetwork private constructor() {

    private val userService = ServiceCreator.create(UserService::class.java)

    suspend fun getUserInfo(name:String) = userService.getUserInfo(name)

    suspend fun getTweetsByUserName(name:String) = userService.getTweetsByUserName(name)

    companion object {
        fun getInstance(): AppNetwork = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = AppNetwork()
    }
}