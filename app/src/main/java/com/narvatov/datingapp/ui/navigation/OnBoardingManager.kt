package com.narvatov.datingapp.ui.navigation

import com.narvatov.datingapp.data.remotedb.throwNoAfterOnBoardingDestination
import com.narvatov.datingapp.delegate.common.context.ContextDelegate
import com.narvatov.datingapp.delegate.common.context.IContextDelegate
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import org.koin.core.annotation.Single
import java.util.LinkedList

@Single
class OnBoardingManager : IContextDelegate by ContextDelegate {

    private var afterOnBoardingDestination: Destination? = null

    private val ignoreOnBoardingFlow = LinkedList<OnBoardingFlow>()

    suspend fun processOnBoarding(afterProcessDestination: Destination? = null) {
        afterProcessDestination?.let { afterOnBoardingDestination = it }

        val onBoardingFlowDestination = onBoardingFlowOrder.filter {
            ignoreOnBoardingFlow.contains(it).not()
        }.firstOrNull {
            it.shouldShow()
        }

        when {
            onBoardingFlowDestination != null -> {
                navigate(onBoardingFlowDestination, clearBackStack = true)
            }
            afterOnBoardingDestination != null -> {
                finishOnBoarding()
            }
            else -> throwNoAfterOnBoardingDestination()
        }
    }

    private fun finishOnBoarding() {
        navigate(afterOnBoardingDestination!!, clearBackStack = true)
        afterOnBoardingDestination = null

        ignoreOnBoardingFlow.clear()
    }

    fun addIgnoreOnBoardingDestination(onBoardingFlow: OnBoardingFlow) {
        ignoreOnBoardingFlow.add(onBoardingFlow)
    }


    private val onBoardingFlowOrder = listOf(
        OnBoardingFlow.NotificationPermissionOnBoarding,
        OnBoardingFlow.LocationPermissionOnBoarding
    )

}