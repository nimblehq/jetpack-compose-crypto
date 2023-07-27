package co.nimblehq.compose.crypto.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.nimblehq.compose.crypto.domain.model.CoinItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val STARTING_PAGE_INDEX = 1

class CoinPagingSource(
    private val coinRepository: CoinRepository,
    private val query: Query
) : PagingSource<Int, CoinItem>() {

    data class Query(
        val currency: String,
        val order: String,
        val priceChangeInHour: String
    )

    override fun getRefreshKey(state: PagingState<Int, CoinItem>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(
                1
            ) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(
                1
            )
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinItem> =
        withContext(Dispatchers.IO) {
            suspendCoroutine { continuation ->
                runBlocking {
                    val page = params.key ?: STARTING_PAGE_INDEX
                    val size = params.loadSize
                    coinRepository.getCoins(
                        currency = query.currency,
                        priceChangePercentage = query.priceChangeInHour,
                        itemOrder = query.order,
                        page = page,
                        itemPerPage = size
                    ).catch {
                        continuation.resume(LoadResult.Error(it))
                    }.collectLatest { items ->
                        val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                        val nextKey = if (items.isEmpty()) null else page + 1

                        val result = if (params.placeholdersEnabled) {
                            val itemsBefore = page * size
                            val itemsAfter = itemsBefore + items.size
                            LoadResult.Page(
                                data = items,
                                prevKey = prevKey,
                                nextKey = nextKey,
                                itemsAfter = if (itemsAfter > size) size else itemsAfter,
                                itemsBefore = if (page == STARTING_PAGE_INDEX) 0 else itemsBefore,
                            )
                        } else {
                            LoadResult.Page(
                                data = items,
                                prevKey = prevKey,
                                nextKey = nextKey
                            )
                        }
                        continuation.resume(result)
                    }
                }
            }
        }
}
