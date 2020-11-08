package com.example.wechatmomentsdemo.utils

import com.example.wechatmomentsdemo.logic.MyRepository
import com.example.wechatmomentsdemo.logic.network.AppNetwork
import com.example.wechatmomentsdemo.ui.moments.MomentsViewModelFactory


/**
 * 应用程序逻辑控制管理类。
 *
 * 将数据管理类与ViewModel绑定
 */
object InjectorUtil {

    private fun getRepository() = MyRepository.getInstance(AppNetwork.getInstance())

    fun getMomentsFactory() = MomentsViewModelFactory(getRepository())
}