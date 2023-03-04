package com.alex.eyk.gifsearch.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.domain.clipboard.CopyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifInfoViewModel @Inject constructor(
    private val copyUseCase: CopyUseCase
) : ViewModel() {

    sealed class Event {

        data class OpenUrl(val url: String) : Event()

        data class NotifyCopied(val url: String) : Event()
    }

    companion object {

        private const val GIF_URL_LABEL = "gif url"
    }

    lateinit var gif: Gif

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun onUrlClick() {
        viewModelScope.launch {
            eventChannel.send(
                Event.OpenUrl(gif.shortUrl)
            )
        }
    }

    fun onUrlLongClick() {
        viewModelScope.launch {
            copyUseCase.copy(
                GIF_URL_LABEL,
                data = gif.shortUrl
            )
            eventChannel.send(
                Event.NotifyCopied(gif.shortUrl)
            )
        }
    }
}
