package com.kaustavjaiswal.omdbsampleapp.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.kaustavjaiswal.omdbsampleapp.databinding.ActivitySearchShowsBinding
import com.kaustavjaiswal.omdbsampleapp.utilities.ConnectionUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchShowsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchShowsBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapter = ShowsAdapter()
    private var searchJob: Job? = null
    private var connected = false
    private var reportedFailure = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchShowsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initConnectionCheck()
        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, (binding.searchOmdb.text?.trim() ?: "").toString())
    }

    private fun initConnectionCheck() {
        ConnectionUtils.getConnectionLiveData(applicationContext)
            .observe(this, Observer { connectionStatus ->
                this.connected = connectionStatus
                if (connected.not()) {
                    setInternetError()
                    viewModel.forceRefresh = false
                } else {
                    setConnectionEstablished()
                }
            })
    }

    private fun setConnectionEstablished() {
        viewModel.forceRefresh = true
        updateShows()
        binding.inputLayout.isErrorEnabled = false
    }

    private fun setInternetError() {
        connectionToast()
        binding.inputLayout.isErrorEnabled = true
        binding.inputLayout.error = "Please Check Internet Connection"
    }

    private fun initAdapter() {
        binding.list.layoutManager = GridLayoutManager(this, 2)
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                handleLoadingScenario()
            } else {
                displayRecyclerViewDataIfAvailable()
                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Timber.e(error.toString())
                }
            }
        }
        binding.list.adapter = adapter
    }

    private fun displayRecyclerViewDataIfAvailable() {
        handlerWithDelay(300) {
            if (binding.list.adapter?.itemCount ?: 0 > 0) {
                handleNotLoadingScenario()
            } else {
                handleErrorScenario()
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun initSearch(query: String) {
        binding.searchOmdb.setText(query)
        binding.inputLayout.setEndIconOnClickListener {
            checkForLastReportedFailure()
            updateShows()
        }
        binding.searchOmdb.doAfterTextChanged {
            lifecycleScope.launch {
                delay(300)  //debounce timeOut
                checkForLastReportedFailure()
                updateShows(false)
            }
        }
        binding.searchOmdb.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.searchOmdb.clearFocus()
                checkForLastReportedFailure()
                updateShows()
                true
            } else {
                false
            }
        }
        binding.searchOmdb.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.searchOmdb.clearFocus()
                checkForLastReportedFailure()
                updateShows()
                true
            } else {
                false
            }
        }

        binding.retryButton.setOnClickListener {
            if (connected.not()) {
                connectionToast()
            }
            adapter.retry()
        }
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchShows(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun updateShows(dismissKeyBoard: Boolean = true) {
        if (dismissKeyBoard) dismissKeyboard()
        val searchText = (binding.searchOmdb.text?.trim() ?: "").toString()
        if (searchText.isNotEmpty()) {
            search(searchText)
        }
    }

    private fun handleErrorScenario() {
        binding.list.isVisible = false
        binding.progressBar.isVisible = false
        binding.retryButton.isVisible = true
        binding.animationView.isVisible = true
    }

    private fun handleNotLoadingScenario() {
        binding.list.isVisible = true
        binding.retryButton.isVisible = false
        binding.progressBar.isVisible = false
        binding.animationView.isVisible = false
    }

    private fun handleLoadingScenario() {
        binding.progressBar.isVisible = true
        binding.retryButton.isVisible = false
        binding.animationView.isVisible = false
    }

    private fun checkForLastReportedFailure() {
/*        if (reportedFailure) {
            viewModel.forceRefresh = true
            reportedFailure = false
        }*/
    }

    private fun connectionToast() {
        Toast.makeText(
            this,
            "Issue detected with internet connection!",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun dismissKeyboard() {
        binding.searchOmdb.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchOmdb.windowToken, 0)
    }

    inline fun handlerWithDelay(duration: Long, crossinline action: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            action()
        }, duration)
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}
