package jihun.bang.cryptocoins.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jihun.bang.cryptocoins.api.ExchangeApi
import jihun.bang.cryptocoins.data.Market
import jihun.bang.cryptocoins.data.Ticker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExchangeViewModel(private val api: ExchangeApi) : ViewModel() {
    private val _marketNames = MutableLiveData<List<Market>>()
    val marketNames: LiveData<List<Market>>
        get() = _marketNames

    private val _tickers = MutableLiveData<List<Ticker>>()
    val tickers: LiveData<List<Ticker>>
        get() = _tickers

    private suspend fun update() {
        marketNames.value?.let { marketNames ->
            val response = api.getTickers(
                marketNames.joinToString(",") { it.market }
            )
            response.onEach { ticker ->
                ticker.imageUrl = "https://cryptoicons.org/api/icon/${ticker.market.split('-')[1].toLowerCase()}/200"
                marketNames.findLast { ticker.market == it.market }?.korean_name?.let {
                    ticker.market = "${it}\n" + ticker.market.replace('-', '/')
                }
            }
            _tickers.postValue(response)
        }
    }

    init {
        viewModelScope.launch {
            _marketNames.postValue(
                api.getCoins().filter { it.market.contains("KRW") }
            )
            while (true) {
                update()
                delay(100)
            }
        }
    }
}