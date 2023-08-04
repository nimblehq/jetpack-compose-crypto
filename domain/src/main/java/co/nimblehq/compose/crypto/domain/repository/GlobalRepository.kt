package co.nimblehq.compose.crypto.domain.repository

import kotlinx.coroutines.flow.Flow

interface GlobalRepository {
    fun isNetworkConnected(): Flow<Boolean?>
}
