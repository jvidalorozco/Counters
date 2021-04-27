package com.cornershop.counterstest.presentation.main

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.*
import android.widget.Toast
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.commons.NetworkUtils
import com.cornershop.counterstest.databinding.FragmentNewCounterBinding
import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.presentation.viewmodels.MainScreenViewModel
import com.cornershop.counterstest.presentation.viewmodels.SharedViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewCounterFragment : Fragment() {

    private var _binding: FragmentNewCounterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by sharedViewModel()
    private val viewModelNew: MainScreenViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewCounterBinding.inflate(inflater, container, false)

        binding.btClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btNewCounter.setOnClickListener {


            if(binding.textNewCounter.text.isNullOrEmpty()){
                binding.textNewCounter.error = resources.getString(R.string.error_creating_counter_title)
                return@setOnClickListener
            }
            binding.textNewCounter.error = null

            binding.cpNewCounter.show()
            binding.btNewCounter.visibility = View.GONE

            if(!NetworkUtils.getNetworkStatus(requireContext())){
                dialog(
                    resources.getString(
                        R.string.error_deleting_counter_title
                    )
                )
                binding.cpNewCounter.hide()
                binding.btNewCounter.visibility = View.VISIBLE

                return@setOnClickListener
            }


            val newCounter = Counters("", binding.textNewCounter.text.toString(), 0)
            viewModelNew.newCounter(newCounter)
            viewModel.text.value = ""


            GlobalScope.launch {
                delay(2000)
                GlobalScope.launch(Dispatchers.Main) {

                    findNavController().popBackStack()
                }
            }


        }




        val text = resources.getString(R.string.create_counter_disclaimer).toSpannable()
        text[text.length - 9 until text.length] = object: ClickableSpan(){
            override fun onClick(view: View) {
                Navigation.findNavController(view).navigate(R.id.action_newCounterFragment2_to_exampleFragment)
            }
        }

        // Make the text view text clickable
        binding.tvExamples.movementMethod = LinkMovementMethod()
        binding.tvExamples.text = text
        binding.viewModel = viewModel


        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_new_counter, menu)
    }


    private fun dialog(counterText: String){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.error_creating_counter_title))
            .setMessage(resources.getString(R.string.connection_error_description))
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                // Respond to negative button press
            }
            .show()
    }


}
