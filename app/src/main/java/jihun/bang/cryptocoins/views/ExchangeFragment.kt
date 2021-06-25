package jihun.bang.cryptocoins.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jihun.bang.cryptocoins.adapters.ExchangeAdapter
import jihun.bang.cryptocoins.data.BookMarkDB
import jihun.bang.cryptocoins.databinding.ActivityMainBinding
import jihun.bang.cryptocoins.databinding.FragmentExchangeBinding
import jihun.bang.cryptocoins.viewModels.ExchangeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeFragment : Fragment() {
    private val viewModel: ExchangeViewModel by viewModel()
    private val coinImages = mutableMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentExchangeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = ExchangeAdapter(coinImages)
        binding.coinList.adapter = adapter

        binding.coinStar.setOnClickListener {
            viewModel.viewState.postValue(ExchangeViewModel.ViewState.BOOK_MARK)
        }
        binding.coinKr.setOnClickListener {
            viewModel.viewState.postValue(ExchangeViewModel.ViewState.KRW)
        }

        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: ExchangeAdapter) {
        viewModel.images.observe(viewLifecycleOwner) {
            it.forEach {
                coinImages[it.symbol] = it.image
            }
        }
        viewModel.tickers.observe(viewLifecycleOwner) { coins ->
            adapter.submitList(
                coins.filter { ticker ->
                    when (viewModel.viewState.value) {
                        ExchangeViewModel.ViewState.BOOK_MARK -> {
                            println("${ticker.getSymbol()} = ${viewModel.starCoins.contains(ticker.getSymbol())}")
                            viewModel.starCoins.contains(ticker.getSymbol())
                        }
                        else -> true
                    }
                }.sortedByDescending { it.acc_trade_price_24h }
            )
        }
    }
}