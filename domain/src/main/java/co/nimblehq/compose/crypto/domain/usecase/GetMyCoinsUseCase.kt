package co.nimblehq.compose.crypto.domain.usecase

import co.nimblehq.compose.crypto.domain.model.CoinItem
import co.nimblehq.compose.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyCoinsUseCase @Inject constructor(private val repository: CoinRepository) {

    fun execute(): Flow<UseCaseResult<List<CoinItem>>> {
        // TODO Update the request param
        return repository.getTrendingList(
            coinIds = emptyList(),
            currency = "",
            priceChangePercentage = "",
            itemOrder = "",
            itemPerPage = 2,
            page = 1
        ).map { data -> UseCaseResult.Success(data) }
            .catch { exception -> error(UseCaseResult.Error(exception)) }
    }
}