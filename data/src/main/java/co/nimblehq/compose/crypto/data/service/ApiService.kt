package co.nimblehq.compose.crypto.data.service

import co.nimblehq.compose.crypto.data.response.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}
