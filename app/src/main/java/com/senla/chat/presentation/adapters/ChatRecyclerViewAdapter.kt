package com.senla.chat.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.senla.chat.R
import com.senla.chat.models.ChatMessage

class ChatRecyclerViewAdapter(
    var messages: List<ChatMessage> = listOf(),
    private val id:String
) : RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>() {

    fun updateMessagesList(messages: List<ChatMessage>?) {
        messages?.let {
            this.messages = it
            notifyDataSetChanged()
        }
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
        return if (messages[position].senderId == id) {
            VIEW_TYPE_COMPANION_MESSAGE
        } else {
            VIEW_TYPE_MY_MESSAGE
        }
    }

    override fun getItemCount() = messages.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val message: TextView = view.findViewById(R.id.tvMessage)

        fun bind(chatMessage: ChatMessage?) {
            chatMessage?.let { message.text = it.message }
        }
    }

    companion object {
        private const val VIEW_TYPE_MY_MESSAGE = 0
        private const val VIEW_TYPE_COMPANION_MESSAGE = 1
    }

}
