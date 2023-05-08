package co.nimblehq.compose.crypto.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.nimblehq.compose.crypto.domain.model.CoinItem
import co.nimblehq.compose.crypto.domain.repository.CoinPagingSource
import co.nimblehq.compose.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val LIST_ITEM_LOAD_MORE_THRESHOLD = 2

class GetTrendingCoinsPaginationUseCase @Inject constructor(private val repository: CoinRepository) {

    data class Input(
        val currency: String,
        val order: String,
        val priceChangeInHour: String,
        val itemPerPage: Int
    )

    fun execute(input: Input): Flow<PagingData<CoinItem>> {
        return with(input) {
            Pager(
                config = PagingConfig(
                    pageSize = itemPerPage,
                    enablePlaceholders = true,
                    prefetchDistance = LIST_ITEM_LOAD_MORE_THRESHOLD,
                    initialLoadSize = itemPerPage
                ),
                pagingSourceFactory = {
                    CoinPagingSource(
                        repository,
                        CoinPagingSource.Query(
                            currency = currency,
                            order = order,
                            priceChangeInHour = priceChangeInHour
                        )
                    )
                }
            ).flow
        }
    }
}
