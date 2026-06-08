package com.example.sharedflowdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * ViewModel с горячим (hot) потоком SharedFlow, эмитирующим целое число
 * каждые 2 секунды (см. §4 ЛР).
 *
 * Поток запускается в init { } один раз при создании ViewModel и работает
 * в viewModelScope, поэтому при уничтожении ViewModel корутина автоматически
 * отменяется.
 *
 *   _sharedFlow  — изменяемый поток (только внутри ViewModel может emit'ить).
 *   sharedFlow   — read-only представление для UI.
 */
class DemoViewModel : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        sharedFlowInit()
    }

    private fun sharedFlowInit() {
        viewModelScope.launch {
            for (i in 1..1000) {
                delay(2000)
                println("Emitting $i")
                _sharedFlow.emit(i)
            }
        }
    }
}
