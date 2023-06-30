package com.narvatov.datingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ReportViewModel : ViewModel() {

    fun reportFriend(friendId: String, reportReason: String) = launchCatching {

    }

}