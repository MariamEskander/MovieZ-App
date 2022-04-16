package app.telda.task.ui.main.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.telda.task.BuildConfig
import app.telda.task.R
import app.telda.task.data.remote.entities.Movie
import app.telda.task.databinding.ListItemMovieBinding
import app.telda.task.utils.extensions.loadImage
import app.telda.task.utils.extensions.toYear


class MoviesPagedListAdapter(private val onMovieClickListener: SetMovieClickListener) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(
        MovieDiffUtil()
    ) {

    class MovieViewHolder(
        private val binding: ListItemMovieBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(Movie: Movie?, onMovieClicked: SetMovieClickListener) {
            Movie?.apply {
                binding.tvTitle.text = title
                binding.tvOverview.text = overview
                binding.tvYear.text = binding.itemCl.context.getString(R.string.released_at) +" "+releaseDate.toYear()
                if (posterPath!= null)
                binding.img.loadImage(BuildConfig.imageUrl+posterPath)
            }
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemMovieBinding.inflate(inflater, parent, false)
                return MovieViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(getItem(position), onMovieClickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder.from(parent)
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}


interface SetMovieClickListener {
    fun onMovieClicked(item: Movie, position: Int)
}
