package com.app.video.invidi

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.app.video.invidi.listener.ExoPlayerListener
import com.app.video.invidi.viewmodels.Effect
import com.app.video.invidi.viewmodels.MediaPlayerScreenViewModel

@Composable
internal fun MediaPlayerScreen(
    modifier: Modifier,
    mediaPlayerScreenViewModel: MediaPlayerScreenViewModel = viewModel(),
    listener: ExoPlayerListener = ExoPlayerListener(mediaPlayerScreenViewModel)
) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build().apply { addListener(listener) } }
    var playbackPosition = rememberSaveable { mutableLongStateOf(0L) } // Save playback position
    val state by mediaPlayerScreenViewModel.stateFlow.collectAsState()
    val url = state.videoUrl
    val isAd = state.isAd
    if (isAd) {
        playbackPosition.longValue = exoPlayer.currentPosition
    }
    LaunchedEffect(url, isAd) {
        exoPlayer.stop()
        val item = MediaItem.Builder()
            .setUri(Uri.parse(url))
            .build()
        exoPlayer.setMediaItem(item)
        if (!isAd) {
            exoPlayer.seekTo(playbackPosition.longValue)
        }
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }
    DisposableEffect(Unit) {
        onDispose {
            playbackPosition.longValue = exoPlayer.currentPosition
            listener.close()
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            }
        )
    }
    LaunchedEffect(mediaPlayerScreenViewModel) {
        mediaPlayerScreenViewModel.effect.collect { effect ->
            when (effect) {
                is Effect.ShowToastErrorMessage -> Toast.makeText(
                    context, effect.errorMessage ?: "An Unknown error happened try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
