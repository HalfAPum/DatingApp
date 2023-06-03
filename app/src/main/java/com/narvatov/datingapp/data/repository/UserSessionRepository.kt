package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.model.local.User
import org.koin.core.annotation.Single

@Single
class UserSessionRepository {

    var user: User = User.emptyUser

}