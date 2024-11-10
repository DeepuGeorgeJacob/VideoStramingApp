package com.app.video.invidi.viewmodels

import comn.app.data.provider.StreamLink
import comn.app.data.provider.VideoStreamDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MediaPlayerScreenViewModel @Inject constructor(videoStreamDataProvider: VideoStreamDataProvider) :
    BaseViewModel<MediaPlayerScreenState, Event, Effect>(MediaPlayerScreenState()) {

    override fun onEvent(event: Event) {
        when (event) {
            Event.Loading -> {
                // Show loading indication
            }


            Event.Playing -> {
                // Playback started
            }

            Event.VideoEnded -> {
                // Load Next Video with ads
            }

            Event.Paused -> {
                // Complete the code if any action required
            }

            Event.StartAd -> {
                // If we need to place different ad update the below Logic
                updateState(state.copy(videoUrl = links.filter { it is StreamLink.AdLink }
                    .first().url, isAd = true))
            }

            Event.AdEnded -> {
                // If we need to place different video update the below Logic
                updateState(
                    state.copy(
                        videoUrl = links.filter { it is StreamLink.VideoLink }
                            .first().url,
                        isAd = false,
                        isAdAlreadyDisplayed = true
                    )
                )
            }

            is Event.ErrorPlayingVideo -> {
                emitSideEffect(effect = Effect.ShowToastErrorMessage(errorMessage = event.errorMessage))
            }
        }
    }

    private val links = mutableListOf<StreamLink>()

    init {
        links.addAll(videoStreamDataProvider.getStreamDataLink())
        updateState(state.copy(videoUrl = links.first().url))
    }


}

internal sealed interface Event {
    object Loading : Event
    object Playing : Event
    object VideoEnded : Event
    object Paused : Event
    object StartAd : Event
    object AdEnded : Event
    data class ErrorPlayingVideo(val errorMessage: String?) : Event
}

internal data class MediaPlayerScreenState(
    val videoUrl: String? = null,
    val isAd: Boolean = false,
    val isAdAlreadyDisplayed: Boolean = false
)

internal interface Effect {
    data class ShowToastErrorMessage(val errorMessage: String?) : Effect
}


