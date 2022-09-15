package co.nimblehq.compose.crypto.data.repository

import co.nimblehq.compose.crypto.data.flowTransform
import co.nimblehq.compose.crypto.data.model.response.*
import co.nimblehq.compose.crypto.data.service.ApiService
import co.nimblehq.compose.crypto.domain.model.*
import co.nimblehq.compose.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinRepositoryImpl(
    private val api: ApiService
) : CoinRepository {

    override fun getCoins(
        coinIds: List<String>?,
        currency: String,
        priceChangePercentage: String,
        itemOrder: String,
        itemPerPage: Int,
        page: Int
    ): Flow<List<CoinItem>> = flowTransform<List<CoinItemResponse>> {
        api.getCoins(
            coinIds = coinIds?.joinToString(separator = ","),
            currency = currency,
            priceChangePercentage = priceChangePercentage,
            itemOrder = itemOrder,
            itemPerPage = itemPerPage,
            page = page
        )
    }.map { coinResponses -> coinResponses.map { it.toModel() } }

    override fun getCoinDetail(
        coinId: String,
        localization: Boolean,
        tickers: Boolean,
        marketData: Boolean,
        communityData: Boolean,
        developerData: Boolean,
        sparkline: Boolean
    ): Flow<CoinDetail> = flowTransform<CoinDetailResponse> {
        api.getCoin(
            coinId = coinId,
            localization = localization,
            tickers = tickers,
            marketData = marketData,
            communityData = communityData,
            developerData = developerData,
            sparkline = sparkline,
        )
    }.map { it.toModel() }

    override fun getCoinPrices(
        coinId: String,
        currency: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Flow<List<CoinPrice>> = flowTransform<CoinPriceResponse> {
        api.getCoinPrices(
            coinId = coinId,
            currency = currency,
            fromTimestamp = fromTimestamp,
            toTimestamp = toTimestamp
        )
    }.map { it.toModel() }
}
