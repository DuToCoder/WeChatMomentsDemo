package com.example.wechatmomentsdemo.logic

import com.example.wechatmomentsdemo.logic.model.MomentInfoBean
import com.example.wechatmomentsdemo.logic.network.AppNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * 数据管理
 *
 */
class MyRepository private constructor(private val appNetwork: AppNetwork) {
    /**
     * 查询用户信息
     */
    suspend fun getInfo(name: String = "jsmith") = withContext(Dispatchers.IO) {

        val deferredUserInfo = async { appNetwork.getUserInfo(name) }

        val deferredTweetsInfo = async { appNetwork.getTweetsByUserName(name) }

        val response = MomentInfoBean(deferredUserInfo.await(),deferredTweetsInfo.await())

        response
    }

    companion object {
        lateinit var appNetwork: AppNetwork
        fun getInstance(network: AppNetwork): MyRepository {
            appNetwork = network
            return SingletonHolder.holder
        }
    }

    private object SingletonHolder {
        val holder = MyRepository(appNetwork)
    }
}