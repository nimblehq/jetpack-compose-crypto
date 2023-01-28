package co.nimblehq.compose.crypto.data.home.service

import co.nimblehq.compose.crypto.data.home.model.response.CoinItemResponse
import co.nimblehq.compose.crypto.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

@Suppress("LongParameterList")
interface HomeApiService {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("ids") coinIds: String?,
        @Query("vs_currency") currency: String,
        @Query("price_change_percentage") priceChangePercentage: String,
        @Query("order") itemOrder: String,
        @Query("per_page") itemPerPage: Int,
        @Query("page") page: Int
    ): Response<List<CoinItemResponse>>
}
