package com.example.login.entity

data class UserInfo(
    var user_id: Int,
    var username: String,
    var phone: String,
    var password: String
){
    companion object {
        @Volatile
        var sUserInfo: UserInfo? = null
        fun getUserInfo(): UserInfo? {
            return sUserInfo
        }

        fun setUserInfo(userInfo: UserInfo) {
            sUserInfo = userInfo
        }
    }
}