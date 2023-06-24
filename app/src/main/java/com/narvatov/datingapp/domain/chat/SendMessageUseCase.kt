package com.narvatov.datingapp.domain.chat

import com.narvatov.datingapp.data.repository.messages.chat.ChatRepository
import com.narvatov.datingapp.model.local.user.User

class SendMessageUseCase(private val chatRepository: ChatRepository) {

    suspend operator fun invoke(message: String, friend: User) {
        if (message.isBlank()) return

        val processedMessage = message.trim()

        chatRepository.sendMessage(processedMessage, friend)
    }

}