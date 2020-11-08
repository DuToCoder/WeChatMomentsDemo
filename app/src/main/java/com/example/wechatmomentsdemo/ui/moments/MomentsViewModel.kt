package com.example.wechatmomentsdemo.ui.moments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wechatmomentsdemo.extension.showToast
import com.example.wechatmomentsdemo.logic.MyRepository
import com.example.wechatmomentsdemo.logic.model.MomentInfoBean
import com.example.wechatmomentsdemo.utils.ResponseHandler
import kotlinx.coroutines.launch

/**
 * Created by Lim  on 2020/11/7.
 */
class MomentsViewModel(private val repository: MyRepository): ViewModel() {

    var momentsLiveData = MutableLiveData<MomentInfoBean>()

    fun getInfo(){
        viewModelScope.launch {
            try {
                val momentInfoBean = repository.getInfo()
                momentsLiveData.value = momentInfoBean
            } catch (e: Exception) {
                ResponseHandler.getFailureTips(e).showToast()
            }
        }
    }


}