package co.nimblehq.compose.crypto.data.detail.repository

import co.nimblehq.compose.crypto.core.mapping.flowTransform
import co.nimblehq.compose.crypto.data.detail.model.*
import co.nimblehq.compose.crypto.data.service.ApiService
import co.nimblehq.compose.crypto.domain.detail.model.CoinDetail
import co.nimblehq.compose.crypto.domain.detail.model.CoinPrice
import co.nimblehq.compose.crypto.domain.detail.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinRepositoryImpl(
    private val api: ApiService
) : CoinRepository {

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
