package com.senla.chat.presentation.fragments.terms

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.senla.chat.App
import com.senla.chat.R
import com.senla.chat.databinding.FragmentTermsBinding
import com.senla.chat.models.Gender
import com.senla.chat.models.SearchTerms
import javax.inject.Inject

class TermsFragment : Fragment() {
    private var _binding: FragmentTermsBinding? = null
    private val binding get() = _binding!!
    private var searchTerms: SearchTerms =
        SearchTerms(
            yourGender = Gender.MAN,
            yourAge = arrayOf(0, 18),
            otherPersonGender = Gender.MAN,
            otherPersonAge = arrayOf(0, 18)
        )

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<TermsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.termsComponent()
            .create().inject(this)
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
        binding.includeYourInfo.personAgeText.setText(R.string.your_age)
        binding.includeYourInfo.personGenderText.setText(R.string.your_gender)
        initClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClickListeners() {
        binding.includeYourInfo.personGenderManButton.setOnClickListener {
            cleanYourGender()
            it.isSelected = true
            searchTerms.yourGender = Gender.MAN
        }
        binding.includeYourInfo.personGenderWomanButton.setOnClickListener {
            cleanYourGender()
            it.isSelected = true
            searchTerms.yourGender = Gender.WOMAN
        }
        binding.includeOtherPerson.personGenderManButton.setOnClickListener {
            cleanOtherPersonGender()
            it.isSelected = true
            searchTerms.otherPersonGender = Gender.MAN
        }
        binding.includeOtherPerson.personGenderWomanButton.setOnClickListener {
            cleanOtherPersonGender()
            it.isSelected = true
            searchTerms.otherPersonGender = Gender.WOMAN
        }
        binding.includeOtherPerson.person18Button.setOnClickListener {
            cleanOtherPersonAge()
            it.isSelected = true
            searchTerms.otherPersonAge = arrayOf(0, 18)
        }
        binding.includeOtherPerson.personAge1825Button.setOnClickListener {
            cleanOtherPersonAge()
            it.isSelected = true
            searchTerms.otherPersonAge = arrayOf(18, 25)
        }
        binding.includeOtherPerson.personAge2535Button.setOnClickListener {
            cleanOtherPersonAge()
            it.isSelected = true
            searchTerms.otherPersonAge = arrayOf(25, 35)
        }
        binding.includeOtherPerson.personAgeBigger35Button.setOnClickListener {
            cleanOtherPersonAge()
            it.isSelected = true
            searchTerms.otherPersonAge = arrayOf(35, 100)
        }
        binding.includeYourInfo.person18Button.setOnClickListener {
            cleanYourAge()
            it.isSelected = true
            searchTerms.yourAge = arrayOf(0, 18)
        }
        binding.includeYourInfo.personAge1825Button.setOnClickListener {
            cleanYourAge()
            it.isSelected = true
            searchTerms.yourAge = arrayOf(18, 25)
        }
        binding.includeYourInfo.personAge2535Button.setOnClickListener {
            cleanYourAge()
            it.isSelected = true
            searchTerms.yourAge = arrayOf(25, 35)
        }
        binding.includeYourInfo.personAgeBigger35Button.setOnClickListener {
            cleanYourAge()
            it.isSelected = true
            searchTerms.yourAge = arrayOf(35, 100)
        }
        binding.startChatButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(SEARCH_TERMS_BUNDLE_KEY, searchTerms)
            findNavController().navigate(R.id.action_termsFragment_to_chatFragment, bundle)
        }
    }

    private fun cleanYourGender() {
        binding.includeYourInfo.personGenderManButton.isSelected = false
        binding.includeYourInfo.personGenderWomanButton.isSelected = false
    }

    private fun cleanOtherPersonGender() {
        binding.includeOtherPerson.personGenderManButton.isSelected = false
        binding.includeOtherPerson.personGenderWomanButton.isSelected = false
    }

    private fun cleanOtherPersonAge() {
        binding.includeOtherPerson.person18Button.isSelected = false
        binding.includeOtherPerson.personAge1825Button.isSelected = false
        binding.includeOtherPerson.personAge2535Button.isSelected = false
        binding.includeOtherPerson.personAgeBigger35Button.isSelected = false
    }

    private fun cleanYourAge() {
        binding.includeYourInfo.person18Button.isSelected = false
        binding.includeYourInfo.personAge1825Button.isSelected = false
        binding.includeYourInfo.personAge2535Button.isSelected = false
        binding.includeYourInfo.personAgeBigger35Button.isSelected = false
    }

    companion object {
        const val SEARCH_TERMS_BUNDLE_KEY = "SEARCH_TERMS"
    }

}