package com.devjamesp.tradingactivity.chatdetail

data class ChatItem(
    val senderId: String,
    val message: String,
) {
    constructor() : this("", "")
}