package com.example.composesample

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val downloader: Downloader) :
    StateViewModel<MainViewModel.State>() {

    data class State(
        val loading: Boolean = false,
        val progress: Float = 0f,
    )

    fun saveFile(url: String) {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            withContext(Dispatchers.IO) {
                downloader.download(url, "test_file")
            }
            updateState { copy(loading = false) }
        }
    }

    override fun initState(): State = State()
}
