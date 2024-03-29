package com.narvatov.datingapp.model.local.notification

enum class PermissionPreference {
    NONE,
    GRANTED,
    SHOW_RATIONALE,
    DENIED;

    val isNotAllowed: Boolean
        get() = this != GRANTED

    val showOnBoarding: Boolean
        get() = this == NONE || this == SHOW_RATIONALE

}