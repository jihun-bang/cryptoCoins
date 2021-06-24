package jihun.bang.cryptocoins.api

import jihun.bang.cryptocoins.data.Market
import jihun.bang.cryptocoins.data.Ticker
import retrofit2.http.*

interface ExchangeApi {
    @GET("v1/market/all")
    suspend fun getCoins(): List<Market>

    @GET("v1/ticker/")
    suspend fun getTickers(@Query("markets") markets: String): List<Ticker>
}