package com.narvatov.datingapp.domain.chat

import com.narvatov.datingapp.data.repository.messages.chat.ChatRepository
import kotlinx.coroutines.flow.map
import kotlin.math.abs

class ChatMessagesFlowUseCase(private val chatRepository: ChatRepository) {

    operator fun invoke() = chatRepository.chatMessageFlow.map {
        it.runningReduce { chatMessage1, chatMessage2 ->
            when {
                chatMessage1::class != chatMessage2::class -> chatMessage2
                abs(chatMessage1.sendTime - chatMessage2.sendTime) < GROUP_TIME -> chatMessage2
                else -> {
                    chatMessage1.showMessageTime = false
                    chatMessage2
                }
            }
        }
    }

    companion object {
        private const val GROUP_TIME = 60000 * 5
    }
}