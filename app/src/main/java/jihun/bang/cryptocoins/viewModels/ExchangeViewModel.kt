package jihun.bang.cryptocoins.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jihun.bang.cryptocoins.api.ExchangeApi
import jihun.bang.cryptocoins.api.ImageApi
import jihun.bang.cryptocoins.data.DB
import jihun.bang.cryptocoins.data.ImageModel
import jihun.bang.cryptocoins.data.Market
import jihun.bang.cryptocoins.data.Ticker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExchangeViewModel(private val api: ExchangeApi, private val imageApi: ImageApi) :
    ViewModel() {
    private val _marketNames = MutableLiveData<List<Market>>()
    val marketNames: LiveData<List<Market>>
        get() = _marketNames

    private val _tickers = MutableLiveData<List<Ticker>>()
    val tickers: LiveData<List<Ticker>>
        get() = _tickers

    private val _images = MutableLiveData<List<ImageModel>>()
    val images: LiveData<List<ImageModel>>
        get() = _images

    val starCoins = mutableSetOf<String>()
    val viewState = MutableLiveData<ViewState>()

    private suspend fun update() {
        marketNames.value?.let { marketNames ->
            val response = api.getTickers(
                marketNames.joinToString(",") { it.market }
            )
            val result = response.onEach { ticker ->
                marketNames.findLast { ticker.market == it.market }?.korean_name?.let {
                    ticker.market = "${it}\n" + ticker.market.replace('-', '/')
                }
            }.filter { ticker ->
                when (viewState.value) {
                    ViewState.BOOK_MARK -> {
                        starCoins.contains(ticker.getSymbol())
                    }
                    else -> true
                }
            }
            _tickers.postValue(result)
        }
    }

    fun updateStar(isAdd: Boolean, coinName: String) {
        when (isAdd) {
            true -> starCoins.add(coinName)
            false -> starCoins.remove(coinName)
        }
    }

    init {
        val savedContacts = DB.db!!.bookMarkDao().getAll()
        starCoins.addAll(savedContacts.map { it.coinName })

        viewModelScope.launch(Dispatchers.IO) {
            _images.value?.let { _images.postValue(imageApi.getImages()) }
            _marketNames.postValue(api.getCoins().filter { it.market.contains("KRW") })

            while (true) {
                update()
                delay(200)
            }
        }
    }

    enum class ViewState {
        KRW, BOOK_MARK
    }
}