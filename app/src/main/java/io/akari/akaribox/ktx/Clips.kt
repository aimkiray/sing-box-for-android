package io.akari.akaribox.ktx

import android.content.ClipData
import io.akari.akaribox.Application

var clipboardText: String?
    get() = Application.clipboard.primaryClip?.getItemAt(0)?.text?.toString()
    set(plainText) {
        if (plainText != null) {
            Application.clipboard.setPrimaryClip(ClipData.newPlainText(null, plainText))
        }
    }
