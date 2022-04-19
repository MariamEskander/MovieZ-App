package app.telda.task.ui.main.wishlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.telda.task.BuildConfig
import app.telda.task.R
import app.telda.task.data.remote.entities.Movie
import app.telda.task.databinding.ListItemMovieBinding
import app.telda.task.utils.extensions.loadImage
import app.telda.task.utils.extensions.toYear


class WishListAdapter(
    private var items: ArrayList<Movie>,
    private val onMovieClicked: SetMovieClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PagesViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PagesViewHolder -> {
                holder.bind(items[position], onMovieClicked)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class PagesViewHolder
    constructor(
        private val binding: ListItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Movie, onMovieClicked: SetMovieClickListener) {
            item.apply {
                binding.tvTitle.text = title
                binding.tvOverview.text = overview
                binding.tvYear.text =
                    binding.itemCl.context.getString(R.string.released_at) + " " + releaseDate.toYear()
                if (posterPath != null)
                    binding.img.loadImage(BuildConfig.imageUrl + posterPath)
                binding.imgFavorite.setImageResource(R.drawable.ic_fav_active)

                binding.imgFavorite.setOnClickListener {
                    onMovieClicked.changeFavoriteStatus(this)
                    items.remove(this)
                    notifyItemRemoved(absoluteAdapterPosition)
                }
            }
        }
    }

    interface SetMovieClickListener {
        fun changeFavoriteStatus(item: Movie)
    }

}

