package com.senla.chat.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.senla.chat.R
import com.senla.chat.model.ChatMessageDto

class ChatRecyclerViewAdapter(
    private var messages: List<ChatMessageDto> = listOf()
) : RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>() {

    companion object {
        const val MESSAGE_OWNER_ME = 0L
        private const val VIEW_TYPE_MY_MESSAGE = 0
        private const val VIEW_TYPE_COMPANION_MESSAGE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_MY_MESSAGE) {
            ViewHolder(inflater.inflate(R.layout.item_chat_right, parent, false))
        } else {
            ViewHolder(inflater.inflate(R.layout.item_chat_left, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = messages[position]
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].authorId == MESSAGE_OWNER_ME) {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_COMPANION_MESSAGE
        }
    }

    override fun getItemCount() = messages.size

    fun updateMessagesList(messages: List<ChatMessageDto>?) {
        messages?.let {
            this.messages = it
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val message: TextView = view.findViewById(R.id.tvMessage)

        fun bind(chatMessageDto: ChatMessageDto?) {
            chatMessageDto?.let { message.text = it.message }
        }
    }
}
