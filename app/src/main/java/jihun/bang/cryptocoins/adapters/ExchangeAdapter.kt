package jihun.bang.cryptocoins.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jihun.bang.cryptocoins.R
import jihun.bang.cryptocoins.data.Ticker
import jihun.bang.cryptocoins.databinding.ListExchangeItemBinding
import jihun.bang.cryptocoins.views.CoinInfoActivity
import java.text.NumberFormat

class ExchangeAdapter(
    private val coinImage: MutableMap<String, String>
) : ListAdapter<Ticker, RecyclerView.ViewHolder>(ExchangeDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExchangeViewHolder(
            ListExchangeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ExchangeViewHolder).bind(item)

        /*
        coinImage[item.getSymbol()]?.let {
            Glide.with(holder.itemView)
                .load(it)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.binding.coinItemImage)
        }
         */
    }

    class ExchangeViewHolder(
        val binding: ListExchangeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ticker) {
            val format = NumberFormat.getInstance().apply {
                maximumFractionDigits = 3
            }
            binding.apply {
                ticker = item
                price = format.format(item.trade_price)
                change = "${format.format(item.signed_change_rate * 100)}%"
                total = "${format.format(item.acc_trade_price_24h / 1000000)}백만"

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, CoinInfoActivity::class.java)
                    intent.putExtra("name", item.getSymbol())
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
                executePendingBindings()
            }
        }
    }
}

private class ExchangeDiffCallback : DiffUtil.ItemCallback<Ticker>() {

    override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
        return oldItem.market == newItem.market
    }

    override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
        return oldItem == newItem
    }
}