package io.akari.akaribox.terminal

data class TailscaleSSHPresentedSession(
    val endpointTag: String,
    val peerHostName: String,
    val peerAddress: String,
    val username: String,
    val hostKeys: List<String>,
)
