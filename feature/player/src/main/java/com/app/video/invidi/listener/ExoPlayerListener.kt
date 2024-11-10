package com.app.video.invidi.listener

import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.app.video.invidi.viewmodels.Event
import com.app.video.invidi.viewmodels.MediaPlayerScreenViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val AD_TIME_INTERVAL = 30L

internal class ExoPlayerListener(private val viewModel: MediaPlayerScreenViewModel) :
    Player.Listener, Runnable {
    private var executor: ExecutorService? = null
    private var playbackElapsedTime = 0L

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
    }

    private fun stopExecuter() {
        executor?.shutdown()
        executor = null
    }

    fun close() {
        stopExecuter()
    }

    override fun run() {
        while (!viewModel.state.isAd) {
            playbackElapsedTime = playbackElapsedTime + 1
            println("XXXXX $playbackElapsedTime")
            if (playbackElapsedTime == AD_TIME_INTERVAL) {
                viewModel.onEvent(Event.StartAd)
                close()
            }
            Thread.sleep(1000)
        }
    }

    override fun onEvents(player: Player, events: Player.Events) {
        if (events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED)) {
            val playbackState = player.playbackState
            when (playbackState) {
                Player.STATE_BUFFERING -> viewModel.onEvent(Event.Loading)
                Player.STATE_READY -> {
                    stopExecuter()
                    if (!viewModel.state.isAdAlreadyDisplayed) {
                        executor = Executors.newSingleThreadExecutor()
                        executor?.execute(this)
                    } else {
                        close()
                    }
                    viewModel.onEvent(Event.Playing)
                }

                Player.STATE_IDLE -> {
                    viewModel.onEvent(Event.Paused)
                    stopExecuter()
                }

                Player.STATE_ENDED -> {
                    if (viewModel.state.isAd) {
                        viewModel.onEvent(Event.AdEnded)
                    } else {
                        viewModel.onEvent(Event.VideoEnded)
                    }

                }
            }
            super.onEvents(player, events)
        }
    }
}