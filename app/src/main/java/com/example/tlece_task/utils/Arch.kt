package com.example.tlece_task.utils

import kotlinx.coroutines.channels.SendChannel

fun <E> SendChannel<E>.tryOffer(element: E): Boolean = try {
    trySend(element).isSuccess
} catch (t: Throwable) {
    false // Ignore
}