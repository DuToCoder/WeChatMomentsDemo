package com.example.wechatmomentsdemo.ui.moments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wechatmomentsdemo.databinding.ActivityMomentsBinding
import com.example.wechatmomentsdemo.extension.showToast
import com.example.wechatmomentsdemo.ui.base.BaseActivity
import com.example.wechatmomentsdemo.utils.InjectorUtil
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.coroutines.delay

class MomentsActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getMomentsFactory()).get(
            MomentsViewModel::class.java
        )
    }

    private val binding by lazy { ActivityMomentsBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        initData()
    }

    private fun setupViews() {
        binding.lifecycleOwner = this
        observe()
    }

    private fun observe() {
        viewModel.momentsLiveData.observe(this, Observer {
            val adapter = MomentsItemAdapter(
                this,
                it.userInfo,
                it.tweetsInfo.subList(
                    0, if (MomentsItemAdapter.PER_PAGE_COUNT > it.tweetsInfo.size)
                        it.tweetsInfo.size else MomentsItemAdapter.PER_PAGE_COUNT
                )
            )
            val layoutManager = LinearLayoutManager(this)
            binding.rvMoments.layoutManager = layoutManager
            binding.rvMoments.adapter = adapter
            binding.rvMoments.setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onRefresh() {
                    //下拉刷新
                    lifecycleScope.launchWhenResumed {
                        delay(500)
                        adapter.currentPage = 1
                        adapter.tweetsList = it.tweetsInfo.subList(
                            0,
                            if (adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT > it.tweetsInfo.size)
                                it.tweetsInfo.size else adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT
                        )
                        adapter.notifyDataSetChanged()

                    }
                    binding.rvMoments.refreshComplete()
                }

                override fun onLoadMore() {
                    //上拉加载
                    if (adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT >= it.tweetsInfo.size) {
                        //没有更多内容
                    } else {
                        lifecycleScope.launchWhenResumed {
                            delay(500)
                            adapter.currentPage++
                            adapter.tweetsList = it.tweetsInfo.subList(
                                0,
                                if (adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT > it.tweetsInfo.size)
                                    it.tweetsInfo.size else adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT
                            )
                            adapter.notifyDataSetChanged()
                        }
                    }
                    binding.rvMoments.loadMoreComplete()
                }

            })
        })
    }


    private fun initData() {
        viewModel.getInfo()
    }
}