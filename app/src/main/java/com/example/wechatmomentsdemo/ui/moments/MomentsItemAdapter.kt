package com.example.wechatmomentsdemo.ui.moments

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wechatmomentsdemo.R
import com.example.wechatmomentsdemo.databinding.ItemTweetsBinding
import com.example.wechatmomentsdemo.databinding.ItemUserinfoBinding
import com.example.wechatmomentsdemo.extension.dp2px
import com.example.wechatmomentsdemo.logic.model.TweetsInfoBean
import com.example.wechatmomentsdemo.logic.model.UserInfoBean

/**
 * Created by Lim  on 2020/11/7.
 */
class MomentsItemAdapter(private val context: Context, private val userInfo: UserInfoBean, var tweetsList: List<TweetsInfoBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //当前显示到多少页
    var currentPage = 1

    companion object {
        const val TYPE_USER = 0
        const val TYPE_TWEET = 1

        const val PER_PAGE_COUNT = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_USER) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_userinfo, parent, false)
            UserViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_tweets, parent, false)
            TweetsViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            holder.bind(userInfo)
        } else if (holder is TweetsViewHolder) {
            holder.bind(tweetsList[position - 1])
        }
    }

    override fun getItemCount(): Int {
        return tweetsList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_USER
        } else {
            TYPE_TWEET
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemUserinfoBinding? = DataBindingUtil.bind(itemView)

        fun bind(userInfo: UserInfoBean) {
            binding?.userInfo = userInfo
        }
    }


    class TweetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemTweetsBinding? = DataBindingUtil.bind(itemView)

        fun bind(tweetsInfo: TweetsInfoBean) {
            binding?.item = tweetsInfo

            if (tweetsInfo.images != null) {
                val gridLayoutManager = when {
                    tweetsInfo.images?.size!! <= 1 -> {
                        GridLayoutManager(binding?.root?.context!!, 1)
                    }
                    tweetsInfo.images?.size == 4 -> {
                        GridLayoutManager(binding?.root?.context!!, 2)
                    }
                    else -> {
                        GridLayoutManager(binding?.root?.context!!, 3)
                    }
                }
                binding.rvTweetsImages.layoutManager = gridLayoutManager
                val commentImageAdapter =
                    CommentImageAdapter(binding.root.context, tweetsInfo.images!!)
                binding.rvTweetsImages.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.right = dp2px(2f)
                        outRect.bottom = dp2px(2f)
                    }
                })
                binding.rvTweetsImages.adapter = commentImageAdapter
            }

            if (tweetsInfo.comments != null) {
                val commentItemAdapter =
                    CommentItemAdapter(binding?.root?.context!!, tweetsInfo.comments!!)
                binding.rvTweetsComment.adapter = commentItemAdapter
            }
        }
    }


}