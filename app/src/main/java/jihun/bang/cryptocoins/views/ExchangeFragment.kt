package jihun.bang.cryptocoins.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jihun.bang.cryptocoins.adapters.ExchangeAdapter
import jihun.bang.cryptocoins.databinding.FragmentExchangeBinding
import jihun.bang.cryptocoins.viewModels.ExchangeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeFragment : Fragment() {
    private val viewModel: ExchangeViewModel by viewModel()

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

        val adapter = ExchangeAdapter()
        binding.coinList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: ExchangeAdapter) {
        viewModel.tickers.observe(viewLifecycleOwner) { coins ->
            adapter.submitList(coins.sortedByDescending { it.acc_trade_price_24h })
        }
    }
}