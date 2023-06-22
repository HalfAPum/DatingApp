package com.narvatov.datingapp.delegate.activity.availability

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.narvatov.datingapp.ui.viewmodel.UserAvailabilityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates.notNull
import kotlin.reflect.KProperty

class UserAvailabilityDelegate : IUserAvailabilityDelegate, DefaultLifecycleObserver {

    private var componentActivity: ComponentActivity by notNull()

    constructor() {
        //Stub
    }

    operator fun getValue(componentActivity: ComponentActivity, property: KProperty<*>): IUserAvailabilityDelegate {
        val userAvailabilityDelegate = UserAvailabilityDelegate(componentActivity)

        componentActivity.lifecycle.addObserver(userAvailabilityDelegate)

        return userAvailabilityDelegate
    }

    constructor(_componentActivity: ComponentActivity) {
        componentActivity = _componentActivity
    }

    private val viewModel by lazy { componentActivity.viewModel<UserAvailabilityViewModel>() }

    override fun onResume(owner: LifecycleOwner) {
        viewModel.value.isUserAvailable = true
    }

    override fun onPause(owner: LifecycleOwner) {
        viewModel.value.isUserAvailable = false
    }

}