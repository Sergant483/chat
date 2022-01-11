package com.senla.chat.models

import java.util.*

data class ChatMessage(
    val senderId: String?,
    val receiverId: String?,
    val message: String?,
    val dataTimeUnit: String?,
    val dateObject: Date?
)