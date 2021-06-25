package jihun.bang.cryptocoins.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jihun.bang.cryptocoins.R
import jihun.bang.cryptocoins.data.BookMark
import jihun.bang.cryptocoins.data.BookMarkDB
import jihun.bang.cryptocoins.data.DB
import jihun.bang.cryptocoins.databinding.ActivityCoinInfoBinding
import jihun.bang.cryptocoins.viewModels.ExchangeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinInfoActivity : AppCompatActivity() {
    private val viewModel: ExchangeViewModel by viewModel()
    private var db: BookMarkDB? = null
    private val binding by lazy {
        ActivityCoinInfoBinding.inflate(layoutInflater).apply { setContentView(this.root) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_info)

        binding.btnStarAdd.setOnClickListener {
            DB.db?.bookMarkDao()?.insert(BookMark(intent.getStringExtra("name")!!))
            viewModel.updateStar(
                true, intent.getStringExtra("name")!!
            )
        }
        binding.btnStarDelete.setOnClickListener {
            DB.db?.bookMarkDao()?.delete(BookMark(intent.getStringExtra("name")!!))
            viewModel.updateStar(
                false, intent.getStringExtra("name")!!
            )
        }
    }
}