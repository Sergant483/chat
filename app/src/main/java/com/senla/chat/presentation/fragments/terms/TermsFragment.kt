package com.senla.chat.presentation.fragments.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.senla.chat.App
import com.senla.chat.databinding.FragmentTermsBinding
import javax.inject.Inject

class TermsFragment : Fragment() {
    private var _binding: FragmentTermsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TermsViewModel
    @Inject
    lateinit var viewModelFactory: TermsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[TermsViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

class TermsViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TermsViewModel() as T
    }
}
