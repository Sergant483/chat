package com.senla.chat.presentation.fragments.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.senla.chat.App
import com.senla.chat.databinding.ChatFragmentBinding
import com.senla.chat.models.SearchTerms
import com.senla.chat.presentation.fragments.terms.TermsFragment
import com.senla.chat.presentation.fragments.terms.TermsViewModel
import javax.inject.Inject

class ChatFragment : Fragment() {
    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<TermsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.chatComponent()
            .create().inject(this)
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
        val searchTerms =
            arguments?.getParcelable<SearchTerms>(TermsFragment.SEARCH_TERMS_BUNDLE_KEY)
        Log.e("TAGG", searchTerms.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}