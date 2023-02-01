package co.nimblehq.compose.crypto.domain.detail.usecase

import co.nimblehq.compose.crypto.domain.detail.model.CoinDetail
import co.nimblehq.compose.crypto.domain.detail.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(private val repository: CoinRepository) {

    fun execute(coinId: String): Flow<CoinDetail> {
        return repository.getCoinDetail(coinId = coinId)
    }
}
