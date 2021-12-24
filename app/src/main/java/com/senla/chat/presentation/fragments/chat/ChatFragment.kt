package com.senla.chat.presentation.fragments.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.senla.chat.App
import com.senla.chat.databinding.ChatFragmentBinding
import com.senla.chat.models.SearchTerms
import com.senla.chat.presentation.fragments.terms.TermsFragment
import javax.inject.Inject

class ChatFragment : Fragment() {
    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ChatViewModel
    @Inject
    lateinit var viewModelFactory: ChatViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ChatViewModel::class.java)
        val searchTerms = arguments?.getParcelable<SearchTerms>(TermsFragment.BUNDLE_KEY)
        Log.e("TAGG",searchTerms.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

class ChatViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel() as T
    }
}