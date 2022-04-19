package app.telda.task.ui.main.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.telda.task.BuildConfig
import app.telda.task.data.remote.entities.Movie
import app.telda.task.databinding.ItemSimilarListBinding
import app.telda.task.utils.extensions.loadImage


class SimilarMoviesAdapter(private var items: ArrayList<Movie>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PagesViewHolder(
            ItemSimilarListBinding.inflate(
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
        private val binding: ItemSimilarListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            if (item.posterPath!= null)
            binding.img.loadImage(BuildConfig.imageUrl + item.posterPath)
        }
    }

}

