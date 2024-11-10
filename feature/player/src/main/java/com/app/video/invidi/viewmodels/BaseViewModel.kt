package com.app.video.invidi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<STATE, EVENT, EFFECT>(initialState: STATE) : ViewModel() {
    var state = initialState
        private set
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: StateFlow<STATE> = _stateFlow.asStateFlow()

    private val _effect = MutableSharedFlow<EFFECT>()
    val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    fun emitSideEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }


    fun updateState(newState: STATE) {
        state = newState
        viewModelScope.launch {
            _stateFlow.emit(state)
        }
    }

    abstract fun onEvent(action: EVENT)
}