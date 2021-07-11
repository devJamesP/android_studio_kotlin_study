package com.devjamesp.tradingactivity

class DBKey private constructor() {
    companion object {
        const val DB_ARTICLES = "Articles" // 중고나라 물품 리스트 저장 db
        const val DB_USERS = "Users" // 가입자 정보  및 룸 생성 시 상위 name DB
        const val CHILD_CHAT = "Chat" // 가입자 정보 및 룸 생성 시 하위 name DB
        const val DB_CHATS = "Chats" // 채팅방
    }
}