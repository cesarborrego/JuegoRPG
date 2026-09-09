package com.hidanhell.juegorphg

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameEngine {

    companion object {
        // '\f' form feed: sentinela de "limpiar pantalla" emitido por AndroidRenderizador
        const val CLEAR_SCREEN = ''
        private const val POLL_INTERVAL_MS = 40L

        init {
            System.loadLibrary("juegorphg")
        }
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    private var pollJob: Job? = null

    private external fun nativeStartGame()
    private external fun nativeObtenerOutput(): String
    private external fun nativeEnviarInput(texto: String)
    private external fun nativeEstaActivo(): Boolean
    private external fun nativeDetener()

    fun start(
        onOutput: (String) -> Unit,
        onFinished: () -> Unit = {}
    ) {
        nativeStartGame()

        pollJob = scope.launch {
            while (isActive) {
                val chunk = nativeObtenerOutput()
                if (chunk.isNotEmpty()) {
                    withContext(Dispatchers.Main) { onOutput(chunk) }
                }
                if (!nativeEstaActivo()) {
                    val tail = nativeObtenerOutput()
                    if (tail.isNotEmpty()) {
                        withContext(Dispatchers.Main) { onOutput(tail) }
                    }
                    withContext(Dispatchers.Main) { onFinished() }
                    break
                }
                delay(POLL_INTERVAL_MS)
            }
        }
    }

    fun send(input: String) {
        nativeEnviarInput(input)
    }

    fun stop() {
        pollJob?.cancel()
        pollJob = null
        nativeDetener()
    }
}
