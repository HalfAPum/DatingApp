package com.narvatov.datingapp.model.local.notification

enum class NotificationPreference {
    NONE,
    GRANTED,
    SHOW_RATIONALE,
    DENIED;

    fun isNotAllowed(): Boolean {
        return this != GRANTED
    }

}