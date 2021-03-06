package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.legalqa.CommentReplies
import com.example.legalqa.Comments
import com.example.legalqa.databinding.CommentsRepliesItemsBinding

class CommentsRepliesAdapter(private val mCommentReplies: List<CommentReplies>, private val actionReplyListener: ReplyActionListener, private val mCommentList: List<Comments>): RecyclerView.Adapter<CommentsRepliesAdapter.CommentRepliesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRepliesViewHolder {
        val binding = CommentsRepliesItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return CommentRepliesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentRepliesViewHolder, position: Int) {
        val currentItem = mCommentReplies[position]
        holder.bind(currentItem, actionReplyListener, mCommentList)
    }

    override fun getItemCount(): Int {
        return mCommentReplies.size
    }

    class CommentRepliesViewHolder(private val binding: CommentsRepliesItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            currentItem: CommentReplies,
            actionReplyListener: ReplyActionListener,
            mCommentList: List<Comments>
        ) {
            binding.apply {
                listener = actionReplyListener
                //= currentItem
                //Log.e(TAG, "bind_commentsReplies: $currentItem ", )
                comments = currentItem
            }
        }

        fun addComment(commentsReplies: CommentReplies) {
            val _commentList = MutableLiveData(mCommentList)
            val _repliesList = mutableListOf(mCommentReplies)
            val newList = _commentList.value ?: mutableListOf()
            if(commentsReplies.parentCommentId != null) {
                newList.find { it.commentId == commentsReplies.parentCommentId }?.commentReplies?.add(commentsReplies)
            } else {
                newList.add(shortComment)
            }
            _commentList.value = newList
        }
    }



}

class ReplyActionListener(val clickListener: () -> Unit, val onSubReplyClic: () -> Unit){
    fun onReplyThreeDotsClick() = clickListener()
    fun onSubReplyClick() = onSubReplyClic()
}