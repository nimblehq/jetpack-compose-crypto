package co.nimblehq.compose.crypto.data.service

import co.nimblehq.compose.crypto.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

@Suppress("LongParameterList")
interface ApiService {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("ids") coinIds: String?,
        @Query("vs_currency") currency: String,
        @Query("price_change_percentage") priceChangePercentage: String,
        @Query("order") itemOrder: String,
        @Query("per_page") itemPerPage: Int,
        @Query("page") page: Int
    ): Response<List<CoinItemResponse>>

    @GET("coins/{id}")
    suspend fun getCoin(
        @Path("id") coinId: String,
        @Query("localization") localization: Boolean,
        @Query("tickers") tickers: Boolean,
        @Query("market_data") marketData: Boolean,
        @Query("community_data") communityData: Boolean,
        @Query("developer_data") developerData: Boolean,
        @Query("sparkline") sparkline: Boolean
    ): Response<CoinDetailResponse>

    @GET("coins/{id}/market_chart/range")
    suspend fun getCoinPrices(
        @Path("id") coinId: String,
        @Query("vs_currency") currency: String,
        @Query("from") fromTimestamp: Long,
        @Query("to") toTimestamp: Long
    ): Response<CoinPriceResponse>
}
