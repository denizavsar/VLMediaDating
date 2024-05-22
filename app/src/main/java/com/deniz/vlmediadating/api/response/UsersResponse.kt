package com.deniz.vlmediadating.api.response

import com.deniz.vlmediadating.api.data.Info
import com.deniz.vlmediadating.api.data.User
import java.io.Serializable

data class UsersResponse(
    val info: Info,
    val results: List<User>
): Serializable
