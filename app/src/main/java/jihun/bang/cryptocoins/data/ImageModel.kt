package jihun.bang.cryptocoins.data

import java.math.BigInteger

data class ImageModel(
    val algorithmic_rating: Double,
    val id: String,
    val image: String,
    val is_coin_of_the_week: Boolean,
    val last_update: String,
    val manual_rating: Double,
    val marketcap: BigInteger,
    val name: String,
    val price: Double,
    val price_24h_percentage_change: Double,
    val price_7d_percentage_change: Double,
    val rank: Int,
    val symbol: String,
    val ticker_price_24h: List<Double>
)