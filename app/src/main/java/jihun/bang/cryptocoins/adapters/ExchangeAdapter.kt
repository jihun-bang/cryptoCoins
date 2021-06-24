package jihun.bang.cryptocoins.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jihun.bang.cryptocoins.R
import jihun.bang.cryptocoins.data.Ticker
import jihun.bang.cryptocoins.databinding.ListExchangeItemBinding
import java.io.FileNotFoundException
import java.text.NumberFormat

class ExchangeAdapter : ListAdapter<Ticker, RecyclerView.ViewHolder>(ExchangeDiffCallback()) {
    private val failList = mutableListOf<String>()
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

        if (!failList.contains(item.imageUrl)) {
            runCatching {
                Glide.with(holder.itemView.context)
                    .load(item.imageUrl)
                    .placeholder(R.mipmap.ic_launcher)
            }.onSuccess {
                it.into(holder.binding.coinItemImage)
            }.onFailure {
                failList.add(item.imageUrl)
            }
        }
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
