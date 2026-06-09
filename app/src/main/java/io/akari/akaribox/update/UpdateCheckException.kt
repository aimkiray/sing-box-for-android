package io.akari.akaribox.update

sealed class UpdateCheckException : Exception() {
    class TrackNotSupported : UpdateCheckException()
}
