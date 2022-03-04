package takutaku.app.jisuityokin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import takutaku.app.jisuityokin.databinding.MemoItemBinding

class MemoAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Memo, MemoViewHolder>(diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = MemoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MemoViewHolder(
    private val binding: MemoItemBinding,
    private val listener: OnItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(memo: Memo) {
        binding.dateTextView.text = memo.date
        binding.checkTextView.text = memo.check
        binding.contentTextView.text = memo.content

        binding.container.setOnClickListener {
            listener.onItemClick(memo)
        }
    }
}

private val diffUtilItemCallback = object : DiffUtil.ItemCallback<Memo>() {
    override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
        return oldItem.id == newItem.id
    }
}

interface OnItemClickListener {
    fun onItemClick(memo: Memo)
}