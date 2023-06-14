package com.narvatov.datingapp.data.remotedb

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

fun <M : HashMap<String, String>> M.putAll(vararg pairs: Pair<String, String>): M {
    return this.apply { putAll(pairs) }
}

fun DocumentSnapshot.requestString(field: String): String {
    return getString(field) ?: throw NoSuchFieldError("Field $field is EMPTY")
}

suspend fun <T> Task<T>.awaitUnit() {
    await()
}