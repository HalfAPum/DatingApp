package com.narvatov.datingapp.model.local.admob

sealed interface AdMobEvent {

    object LoadAdMob : AdMobEvent

    object ShowAdMob : AdMobEvent

}