package com.senla.chat.presentation.fragments.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.senla.chat.databinding.FragmentChatBinding
import com.senla.chat.model.ChatMessageDto
import com.senla.chat.presentation.adapters.ChatRecyclerViewAdapter
import com.senla.chat.presentation.decorators.ChatRecyclerViewDecorator

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChatViewModel
    private lateinit var chatAdapter: ChatRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        initMessages()
        loadMessages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMessages() {
        chatAdapter = ChatRecyclerViewAdapter()
        context?.let {
            binding.rvChat.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = chatAdapter
                addItemDecoration(ChatRecyclerViewDecorator())
            }
        }
    }

    private fun loadMessages() {
        // Fake
        chatAdapter.updateMessagesList(
            listOf(
                ChatMessageDto(0, "Hello!"),
                ChatMessageDto(0, "How are you?"),
                ChatMessageDto(30, "I'm fine!")
            )
        )
    }
}
