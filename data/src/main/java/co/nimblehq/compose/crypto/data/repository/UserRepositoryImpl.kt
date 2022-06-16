package co.nimblehq.compose.crypto.data.repository

import co.nimblehq.compose.crypto.data.response.toUsers
import co.nimblehq.compose.crypto.data.service.ApiService
import co.nimblehq.compose.crypto.domain.model.User
import co.nimblehq.compose.crypto.domain.repository.UserRepository

class UserRepositoryImpl constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers().toUsers()
    }
}
