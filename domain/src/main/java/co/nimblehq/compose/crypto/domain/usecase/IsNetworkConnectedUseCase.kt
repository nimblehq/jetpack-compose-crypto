package co.nimblehq.compose.crypto.domain.usecase

import co.nimblehq.compose.crypto.domain.repository.GlobalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsNetworkConnectedUseCase @Inject constructor(private val repository: GlobalRepository) {
    operator fun invoke(): Flow<Boolean?> = repository.isNetworkConnected()
}
