package co.nimblehq.compose.crypto.domain.repository

import co.nimblehq.compose.crypto.domain.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>
}
