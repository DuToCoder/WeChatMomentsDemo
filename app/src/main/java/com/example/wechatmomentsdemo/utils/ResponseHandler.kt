package com.example.wechatmomentsdemo.utils

import android.text.TextUtils
import com.example.wechatmomentsdemo.R
import com.example.wechatmomentsdemo.extension.logW
import com.google.gson.JsonSyntaxException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 获取网络请求返回的异常信息。
 *
 */
object ResponseHandler {

    private const val TAG = "ResponseHandler"

    /**
     * 当网络请求没有正常响应的时候，根据异常类型进行相应的处理。
     * @param e 异常实体类
     */
    fun getFailureTips(e: Throwable?): String {
        logW(TAG, "getFailureTips exception is ${e?.message}")
        return when (e) {
            is ConnectException -> GlobalUtil.getString(R.string.network_connect_error)
            is SocketTimeoutException -> GlobalUtil.getString(R.string.network_connect_timeout)
            is ResponseCodeException -> GlobalUtil.getString(R.string.network_response_code_error) + e.responseCode
            is NoRouteToHostException -> GlobalUtil.getString(R.string.no_route_to_host)
            is UnknownHostException -> GlobalUtil.getString(R.string.network_error)
            is JsonSyntaxException -> GlobalUtil.getString(R.string.json_data_error)
            else -> {
                if (e != null) {
                    val message = e.message
                    if (TextUtils.isEmpty(message)) {
                        GlobalUtil.getString(R.string.unknown_error)
                    } else {
                        message!!
                    }
                } else GlobalUtil.getString(R.string.unknown_error)
            }
        }
    }
}

class ResponseCodeException(val responseCode: Int) :
    RuntimeException("Http request failed with response code $responseCode")