package com.example.wechatmomentsdemo.ui.moments

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wechatmomentsdemo.R
import com.example.wechatmomentsdemo.databinding.ItemTweetsCommentBinding
import com.example.wechatmomentsdemo.logic.model.TweetsInfoBean

/**
 * Created by Lim  on 2020/11/8.
 */
class CommentItemAdapter(private val context: Context, private val commentList: List<TweetsInfoBean.Comment>) :
    RecyclerView.Adapter<CommentItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tweets_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemTweetsCommentBinding? = DataBindingUtil.bind(itemView)

        fun bind(comment: TweetsInfoBean.Comment) {
            val nickSpannableString = SpannableString(comment.sender.nick + ":")
            nickSpannableString.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        binding?.root?.context!!,
                        R.color.color_128bd5
                    )
                ), 0, nickSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.tvCommentContent.text =
                SpannableStringBuilder().append(nickSpannableString).append(comment.content)
        }
    }
}