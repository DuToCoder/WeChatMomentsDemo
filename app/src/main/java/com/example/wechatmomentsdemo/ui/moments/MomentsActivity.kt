package com.example.wechatmomentsdemo.ui.moments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.wechatmomentsdemo.databinding.ActivityMomentsBinding
import com.example.wechatmomentsdemo.extension.logD
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
            (binding.rvMoments.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            binding.rvMoments.setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onRefresh() {
                    //下拉刷新
                    lifecycleScope.launchWhenResumed {
                        delay(500)
                        adapter.currentPage = 1

                        val newList =  it.tweetsInfo.subList(
                            0,
                            if (adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT > it.tweetsInfo.size)
                                it.tweetsInfo.size else adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT
                        )

                        val calculateDiff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                            override fun getOldListSize(): Int {
                                return adapter.tweetsList.size
                            }

                            override fun getNewListSize(): Int {
                                return newList.size
                            }

                            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                                return newList[newItemPosition] == adapter.tweetsList[oldItemPosition]
                            }

                            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                                return newList[newItemPosition] == adapter.tweetsList[oldItemPosition]
                            }

                        })
                        calculateDiff.dispatchUpdatesTo(adapter)
                        adapter.tweetsList = newList

                        binding.rvMoments.refreshComplete()
                    }
                }

                override fun onLoadMore() {
                    //上拉加载
                    if (adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT >= it.tweetsInfo.size) {
                        //没有更多内容
                        binding.rvMoments.loadMoreComplete()
                    } else {
                        lifecycleScope.launchWhenResumed {
                            delay(500)
                            adapter.currentPage++
                            val oldSize = adapter.tweetsList.size
                            adapter.tweetsList = it.tweetsInfo.subList(
                                0,
                                if (adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT > it.tweetsInfo.size)
                                    it.tweetsInfo.size else adapter.currentPage * MomentsItemAdapter.PER_PAGE_COUNT
                            )
                            adapter.notifyItemRangeChanged(oldSize + 1, adapter.tweetsList.size + 1)
                            binding.rvMoments.loadMoreComplete()
                        }
                    }
                }

            })
        })
    }


    private fun initData() {
        viewModel.getInfo()
        logD("http_request", "getInfo")
    }
}