package com.narvatov.datingapp.data.repository.messages

import com.narvatov.datingapp.data.remotedb.datasource.ConversationRemoteDataSource
import org.koin.core.annotation.Factory

@Factory
class MessagesRepository(
    conversationRemoteDataSource: ConversationRemoteDataSource,
) {

    val conversationFlow = conversationRemoteDataSource.conversationFlow

}