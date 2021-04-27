package com.cornershop.counterstest.presentation.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.cornershop.counterstest.R

import com.cornershop.counterstest.commons.NetworkUtils
import com.cornershop.counterstest.databinding.ActivityMainScreenBinding
import com.cornershop.counterstest.databinding.FragmentListCountersBinding
import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.presentation.adapters.CounterAdapter
import com.cornershop.counterstest.presentation.adapters.CounterItemDetailsLookup
import com.cornershop.counterstest.presentation.adapters.ItemKeyProvider

import com.cornershop.counterstest.presentation.states.MainScreenViewStates
import com.cornershop.counterstest.presentation.viewmodels.MainScreenViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCounterFragment : Fragment(), CounterAdapter.OnItemClickListener {

    // region Members

    private val viewModel by viewModel<MainScreenViewModel>()
    private var _binding: FragmentListCountersBinding? = null
    private val binding get() = _binding!!
    private var actionMode: ActionMode? = null
    private var tracker: SelectionTracker<Counters>? = null
    private lateinit var countersAdapter: CounterAdapter
    private var lstCounters: MutableList<Counters> = mutableListOf()
    private var selectedPostItems: MutableList<Counters> = mutableListOf()

    // endregion


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListCountersBinding.inflate(inflater, container, false)


        observeSearchViewState()
        handleLoadRetry()
        handleLoadSwipe()
        configureSearch()
        handleCallNewCounter()
        setupAdapter()
        observeTracker()
        return binding.root
    }


    private fun setupAdapter() {
        countersAdapter = CounterAdapter(this, lstCounters)
        binding.includesMainScreen.recyclerviewCounters.adapter = countersAdapter
        tracker = SelectionTracker.Builder(
            "mySelection",
            binding.includesMainScreen.recyclerviewCounters,
            ItemKeyProvider(countersAdapter),
            CounterItemDetailsLookup(binding.includesMainScreen.recyclerviewCounters),
            StorageStrategy.createParcelableStorage(Counters::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()

        countersAdapter.tracker = tracker
    }

    private fun observeTracker() {
        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedPostItems = it.selection.toMutableList()

                        if (selectedPostItems.isEmpty()) {
                            actionMode?.finish()
                        } else {
                            if (actionMode == null) actionMode =
                                (activity as MainScreenActivity?)?.startSupportActionMode(
                                    actionModeCallback
                                )
                            actionMode?.title =
                                resources.getString(R.string.n_selected, selectedPostItems.size)
                        }
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickSum(counter: Counters) {
       handleIncrement(counter)
    }

    override fun onClickMinus(counter: Counters) {
        handleDecrement(counter)
    }

    private fun handleCallNewCounter() {
        binding.includesMainScreen.buttonAddCounter.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_listCounterFragment_to_newCounterFragment2)
        }

    }

    private fun handleLoadRetry() {
        binding.includesMainScreen.includesNotInternet.btRetry.setOnClickListener {
            viewModel.loadCounters()
        }


    }

    private fun handleLoadSwipe() {
        binding.includesMainScreen.swipeContainer.setOnRefreshListener {
            viewModel.loadCounters()
        }
    }

    private fun configureSearch() {

        with(binding) {
            searchView.setOnCloseListener {

                return@setOnCloseListener false
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        if (it.isNotEmpty()) {
                            viewModel.onSearch(it)
                            return@let
                        }
                      viewModel.loadCounters()
                    }

                    return true
                }
            })
        }
    }


    private fun observeSearchViewState() {
        viewModel.countersViewState.observe(requireActivity(), Observer { state ->
            lstCounters.clear()
            handleSearchLoading(state)
            binding.includesMainScreen.swipeContainer.isRefreshing = false
            state.countersResult?.let { searchResults ->
                if (state.isSearching && searchResults.isEmpty()) {
                    handleNotSearchResult()
                    return@let
                }

                if (!state.isIncrementOrDecrement && searchResults.isEmpty()) {
                    handleNoCounterYet()
                    return@let
                }
                handleSearchResults(searchResults)
            }

            handleSearchError(state)

        })
    }



    private fun handleSearchLoading(state: MainScreenViewStates) {
        //EspressoIdlingResource.decrement()
        with(binding.includesMainScreen) {
            if (state.isLoading) {
                circularProgressIndicator.show()
           } else {
                circularProgressIndicator.hide()
           }
        }
    }


    private fun handleSearchError(state: MainScreenViewStates) {
        //EspressoIdlingResource.decrement()
        state.error?.run {
            if (state.isIncrementOrDecrement) {
                binding.searchView.isEnabled = false
                return
            }


            with(binding.includesMainScreen) {
                recyclerviewCounters.visibility = View.GONE
                includesNotInternet.root.visibility = View.VISIBLE

            }
            binding.tvItems.visibility = View.GONE
            binding.tvTimes.visibility = View.GONE

        }
    }

    private fun handleNotSearchResult() {

        with(binding.includesMainScreen) {
            recyclerviewCounters.visibility = View.GONE
            includesNotContentYet.root.visibility = View.GONE

            includesNotResult.root.visibility = View.VISIBLE
        }

    }

    private fun handleNoCounterYet() {
        //EspressoIdlingResource.decrement()

        with(binding.includesMainScreen) {
            recyclerviewCounters.visibility = View.GONE
            includesNotResult.root.visibility = View.GONE
            includesNotContentYet.root.visibility = View.VISIBLE
            binding.tvItems.visibility = View.GONE
            binding.tvTimes.visibility = View.GONE
            includesNotInternet.root.visibility = View.GONE
        }


    }

    private fun handleSearchResults(searchResults: List<Counters>) {
        with(binding.includesMainScreen) {
            circularProgressIndicator.hide()

            if (searchResults.isNotEmpty()) {
                binding.sumTimes = resources.getString(R.string.n_times,searchResults.map { it.count }.sum())
                binding.sumItems = resources.getString(R.string.n_items,searchResults.count())
                recyclerviewCounters.visibility = View.VISIBLE
                binding.tvItems.visibility = View.VISIBLE
                binding.tvTimes.visibility = View.VISIBLE
                includesNotContentYet.root.visibility = View.GONE
                includesNotInternet.root.visibility = View.GONE
                includesNotResult.root.visibility = View.GONE
                lstCounters.addAll(searchResults)
                countersAdapter.notifyDataSetChanged()

            }
        }
    }

    private fun handleDecrement(counter: Counters){
        if(!NetworkUtils.getNetworkStatus(requireContext())){
            dialogRetry(counter,

                retryType = RetryType.DECREMENT
            )
            return
        }
        if(counter.count == 0){
            return
        }
        viewModel.decrementCounter(counter)
    }


    private fun handleIncrement(counter: Counters){
        if(!NetworkUtils.getNetworkStatus(requireContext())){
            dialogRetry(counter, retryType = RetryType.INCREMENT
            )
            return
        }
        viewModel.incrementCounter(counter)
    }

    private fun handleDelete(){
        if(!NetworkUtils.getNetworkStatus(requireContext())){
            dialog(
                resources.getString(
                    R.string.error_deleting_counter_title
                )
            )
            return
        }

        viewModel.delete(selectedPostItems.first())
        actionMode?.finish()

    }


    private fun dialogRetry(counter: Counters, retryType: RetryType) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(counter.title)
            .setMessage(resources.getString(
                R.string.error_updating_counter_title,
                counter.title,
                1
            ))
            .setNegativeButton(resources.getString(R.string.retry)) { dialog, which ->
                if(retryType == RetryType.INCREMENT){
                    handleIncrement(counter)
                }
                if(retryType == RetryType.DECREMENT){
                    handleDecrement(counter)
                }
            }
            .setPositiveButton(resources.getString(R.string.dismiss)) { dialog, which ->
                dialog.dismiss()
            }
            .show()
            .show()
    }

    private fun dialog(counterText: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(counterText)
            .setMessage(resources.getString(R.string.connection_error_description))
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun dialogDelete() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(resources.getString(R.string.delete_x_question, selectedPostItems.first().title))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
               dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.delete)) { dialog, which ->
                handleDelete()
            }
            .show()
    }



    private val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            binding.searchView.visibility = View.GONE
            mode?.let {
                val inflater: MenuInflater = it.menuInflater
                inflater.inflate(R.menu.menu_new_counter, menu)
                return true
            }
            return false
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

            when (item?.itemId) {
                R.id.action_share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            resources.getString(R.string.n_per_counter_name, selectedPostItems.first().count, selectedPostItems.first().title)
                        )

                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }

                R.id.action_delete -> {

                    dialogDelete()

                }
            }
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            countersAdapter.tracker?.clearSelection()
            countersAdapter.notifyDataSetChanged()
            actionMode = null
            binding.searchView.visibility = View.VISIBLE
        }

    }

}

enum class RetryType {
    INCREMENT, DECREMENT
}

