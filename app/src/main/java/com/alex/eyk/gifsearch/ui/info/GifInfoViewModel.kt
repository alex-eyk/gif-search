package com.alex.eyk.gifsearch.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.domain.clipboard.CopyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

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

        private const val GIF_DATE_FORMAT = "dd-MM-yyyy"
    }

    lateinit var gif: Gif

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun onUrlClick() {
        viewModelScope.launch {
            eventChannel.send(
                Event.OpenUrl(gif.getOptimalUrl())
            )
        }
    }

    fun onUrlLongClick() {
        viewModelScope.launch {
            copyUseCase.copy(
                GIF_URL_LABEL,
                data = gif.getOptimalUrl()
            )
            eventChannel.send(
                Event.NotifyCopied(gif.getOptimalUrl())
            )
        }
    }

    fun getCreatedDay(): String {
        return getGifDateFormat()
            .format(gif.created)
    }

    fun getTrendingDay(): String {
        return getGifDateFormat()
            .format(gif.trending)
    }

    private fun getGifDateFormat() = SimpleDateFormat(
        GIF_DATE_FORMAT,
        Locale.getDefault()
    )
}
