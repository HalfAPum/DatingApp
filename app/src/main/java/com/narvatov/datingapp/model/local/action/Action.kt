package com.narvatov.datingapp.model.local.action

sealed interface Action {

    sealed interface SignAction : Action {
        object SignInAction : SignAction
        object SignUpAction : SignAction
    }

}
