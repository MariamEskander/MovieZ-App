package app.telda.task.ui.main.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.telda.task.BuildConfig
import app.telda.task.data.remote.entities.Cast
import app.telda.task.databinding.ItemCastListBinding
import app.telda.task.utils.extensions.loadImage


class CastAdapter(private var items: ArrayList<Cast>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PagesViewHolder(
            ItemCastListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PagesViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class PagesViewHolder
    constructor(
        private val binding: ItemCastListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cast) {
            if (item.profile!= null)
            binding.img.loadImage(BuildConfig.imageUrl + item.profile)
            binding.tvName.text = item.name
            binding.ratePopularity.rating = item.popularity.toFloat() / 2
        }
    }

}

