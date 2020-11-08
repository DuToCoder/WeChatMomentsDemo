package com.example.wechatmomentsdemo.ui.moments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wechatmomentsdemo.logic.MyRepository

/**
 * Created by Lim  on 2020/11/7.
 */
class MomentsViewModelFactory(private val repository:MyRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MomentsViewModel(repository) as T
    }
}