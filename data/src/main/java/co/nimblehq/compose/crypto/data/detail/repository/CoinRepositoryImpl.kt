package co.nimblehq.compose.crypto.data.detail.repository

import co.nimblehq.compose.crypto.data.detail.model.toModel
import co.nimblehq.compose.crypto.data.detail.service.CoinDetailApiService
import co.nimblehq.compose.crypto.data.flowTransform
import co.nimblehq.compose.crypto.domain.detail.repository.CoinRepository
import kotlinx.coroutines.flow.map

class CoinRepositoryImpl(
    private val api: CoinDetailApiService
) : CoinRepository {

    override fun getCoinDetail(
        coinId: String,
        localization: Boolean,
        tickers: Boolean,
        marketData: Boolean,
        communityData: Boolean,
        developerData: Boolean,
        sparkline: Boolean
    ) = flowTransform {
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
    ) = flowTransform {
        api.getCoinPrices(
            coinId = coinId,
            currency = currency,
            fromTimestamp = fromTimestamp,
            toTimestamp = toTimestamp
        )
    }.map { it.toModel() }
}
