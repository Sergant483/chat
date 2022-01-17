package com.senla.chat.presentation.fragments.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.senla.chat.App
import com.senla.chat.databinding.FragmentChatBinding
import com.senla.chat.models.ChatMessage
import com.senla.chat.models.ChatState
import com.senla.chat.models.SearchTerms
import com.senla.chat.models.User
import com.senla.chat.presentation.adapters.ChatRecyclerViewAdapter
import com.senla.chat.presentation.decorators.ChatRecyclerViewDecorator
import com.senla.chat.presentation.fragments.terms.TermsFragment
import com.senla.chat.presentation.fragments.utils.LoadingCustomDialog
import com.senla.chat.presentation.fragments.utils.LoadingProgressDialog
import com.senla.chat.presentation.fragments.utils.PreferenceManager
import com.senla.chat.service.CloseService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ChatViewModel> { viewModelFactory }
    private var chatAdapter = ChatRecyclerViewAdapter(emptyList(), "")
    private val chatMessages: MutableList<ChatMessage> = mutableListOf()
    private val preferenceManager by lazy { PreferenceManager(requireContext()) }
    private val loadingCustomDialog by lazy { LoadingCustomDialog(requireActivity(),findNavController()) }
    private val loadingProgressDialog by lazy { LoadingProgressDialog(requireActivity(),findNavController()) }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.chatComponent()
            .create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAGG","onViewCreated")
        initClickListeners()
        initRV()
        initObservers()
        val arguments = arguments?.getParcelable<SearchTerms>(TermsFragment.SEARCH_TERMS_BUNDLE_KEY)
        if (arguments != null) {
            viewModel.doChatStateLoading()
            viewModel.sendYourUserData(arguments, preferenceManager)
            var userFinded = true
            lifecycleScope.launch {
                while (userFinded) {
                    Log.d("TAGG"," delay(2000L)")
                    viewModel.loadReceiverUser(arguments)
                    delay(2000L)
                    Log.d("TAGG","viewModel.loadReceiverUser(arguments)")
                    if (viewModel.receivedUser.value != null) {
                        userFinded = false
                        Log.d("TAGG","userFinded = false")
                        Log.d("TAGG","${viewModel.receivedUser.value}")
                        viewModel.loadReceiverUser(arguments)
                    }
                }
            }
                viewModel.receivedUser.observe(viewLifecycleOwner, Observer {
                        listenMessages(it)
                        sendMessage(it)
                    Log.d("TAGG","listenMessages(it) sendMessage(it)")
                    viewModel.doChatStateDialog()
                })

        }
        //Log.d("TAGG", preferenceManager.getString(ChatViewModel.KEY_USER_ID_IN_DB))
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRV() {
        chatAdapter = ChatRecyclerViewAdapter(listOf(), preferenceManager.getString(KEY_USER_ID))
        context?.let {
            binding.rvChat.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = chatAdapter
                addItemDecoration(ChatRecyclerViewDecorator())
            }
        }
    }

    private fun sendMessage(receiverUser: User) {
        val message: HashMap<String, Any> = hashMapOf()
        message.put(KEY_SENDER_ID, preferenceManager.getString(KEY_USER_ID))
        message.put(KEY_RECEIVER_ID, receiverUser.id)
        message.put(KEY_MESSAGE, binding.inputMessage.text.toString())
        message.put(KEY_TIMESTAMP, Date())
        database.collection(KEY_COLLECTION_CHAT).add(message)
        binding.inputMessage.setText(null)
    }

    private fun listenMessages(receiverUser: User) {
        database.collection(KEY_COLLECTION_CHAT)
            .whereEqualTo(KEY_SENDER_ID, preferenceManager.getString(KEY_USER_ID))
            .whereEqualTo(KEY_RECEIVER_ID, receiverUser.id)
            .addSnapshotListener(eventListener)
        database.collection(KEY_COLLECTION_CHAT)
            .whereEqualTo(KEY_SENDER_ID, receiverUser.id)
            .whereEqualTo(KEY_RECEIVER_ID, preferenceManager.getString(KEY_USER_ID))
            .addSnapshotListener(eventListener)
    }

    private val eventListener = EventListener<QuerySnapshot> { value, error ->
        if (error != null) {
            return@EventListener
        }
        if (value != null) {
            val documentChange = value.documentChanges
            documentChange.forEach { it ->
                if (it.type == DocumentChange.Type.ADDED) {
                    val chatMessage = ChatMessage(
                        senderId = it.document.getString(KEY_SENDER_ID),
                        receiverId = it.document.getString(KEY_RECEIVER_ID),
                        message = it.document.getString(KEY_MESSAGE),
                        dataTimeUnit = getReadableDateTime(it.document.getDate(KEY_TIMESTAMP)),
                        dateObject = it.document.getDate(KEY_TIMESTAMP)
                    )
                    chatMessages.add(chatMessage)
                    chatAdapter.updateMessagesList(chatMessages)
                    viewModel.doSearchStateFalse()
                }
            }
            Collections.sort(
                chatAdapter.messages,
                Comparator { o1, o2 -> o1.dateObject!!.compareTo(o2.dateObject) })
            val count = chatMessages.size
            if (count == 0) {
                chatAdapter.notifyDataSetChanged()
            } else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size, chatMessages.size)
                binding.rvChat.smoothScrollToPosition(chatMessages.size - 1)
            }
            //binding.chatRecyclerView.visibility = View.VISIBLE
        }
        //binding.progressBar.visibility = View.GONE
    }

    private fun getReadableDateTime(date: Date?): String {
        return SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date)
    }

    private fun initClickListeners() {
        binding.buttonSend.setOnClickListener {
            viewModel.receivedUser.value?.let { it -> sendMessage(it) }
            }
    }

    private fun initObservers() {
        viewModel.userId.observe(viewLifecycleOwner, {
            val serviceIntent =
                Intent(requireContext().applicationContext, CloseService::class.java)
            ContextCompat.startForegroundService(requireActivity().baseContext, serviceIntent)
        })
        viewModel.chatState.observe(viewLifecycleOwner, {
            when (it) {
                ChatState.LOADING -> {
                    loadingProgressDialog.startLoading()
                    loadingCustomDialog.isDismiss()
                }
                ChatState.ERROR -> {
                    loadingCustomDialog.startLoading()
                    loadingProgressDialog.isDismiss()
                }
                ChatState.DIALOG -> {
                    loadingCustomDialog.isDismiss()
                    loadingProgressDialog.isDismiss()
                }
            }
        })
    }


    companion object {
        const val KEY_USER_ID = "userId"
        const val KEY_COLLECTION_CHAT = "chat"
        const val KEY_SENDER_ID = "senderId"
        const val KEY_RECEIVER_ID = "receiverId"
        const val KEY_MESSAGE = "message"
        const val KEY_TIMESTAMP = "timestamp"
    }
}
