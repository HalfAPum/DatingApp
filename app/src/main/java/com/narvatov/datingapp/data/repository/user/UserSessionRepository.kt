package com.narvatov.datingapp.data.repository.user

import com.narvatov.datingapp.model.local.user.User
import org.koin.core.annotation.Single

@Single
class UserSessionRepository {

    var user: User = User.emptyUser

}