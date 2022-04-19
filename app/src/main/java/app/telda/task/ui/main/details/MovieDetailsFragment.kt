package app.telda.task.ui.main.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import app.telda.task.BuildConfig
import app.telda.task.R
import app.telda.task.base.BaseFragment
import app.telda.task.base.BaseViewModel
import app.telda.task.data.remote.entities.Cast
import app.telda.task.data.remote.entities.CreditLists
import app.telda.task.data.remote.entities.Movie
import app.telda.task.data.remote.entities.MoviesResponse
import app.telda.task.databinding.FragmentMovieDetailsBinding
import app.telda.task.ui.main.details.adapters.CastAdapter
import app.telda.task.ui.main.details.adapters.SimilarMoviesAdapter
import app.telda.task.utils.MyBounceInterpolator
import app.telda.task.utils.Status
import app.telda.task.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {

    private var item: Movie? = null
    private var movieId: String = ""
    private val viewModel: MovieDetailsViewModel by viewModels()

    private var _binding: FragmentMovieDetailsBinding? = null

    private val binding get() = _binding!!
    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewReady() {
        arguments?.let {
            movieId = arguments?.getString("movieId") ?: ""
            viewModel.getMovieDetails(movieId)
        }
    }

    override fun setObservers() {
        observe(viewModel.detailsStatus) { status ->
            when (status) {
                is Status.Loading -> {
                    binding.nsv.hideView()
                    binding.appbar.hideView()
                    binding.errorLayout.hideView()
                    showDialogLoading()
                }
                is Status.Success<*> -> {
                    hideDialogLoading()
                    setMovieDetails(status.data as Movie)
                    viewModel.getSimilarMovies(movieId)

                }
                is Status.Error -> {
                    hideDialogLoading()
                    showErrorView()
                    showErrorMsg(status.message)
                }
                else -> {}
            }
        }

        observe(viewModel.similarStatus) { status ->
            when (status) {
                is Status.Success<*> -> {
                    setSimilarMovies(status.data as MoviesResponse)
                }
                else -> {}
            }
        }

        observe(viewModel.creditDataStatus) { status ->
            when (status) {
                is Status.Success<*> -> {
                    setCast(status.data as CreditLists)
                }
                else -> {}
            }
        }

        observe(viewModel.checkFavStatus) { status ->
            when (status) {
                is Status.Success<*> -> {
                    val data = status.data as Movie?
                    item?.isFavorite = data != null
                    setFavorite()
                }
                else -> {}
            }
        }

    }

    private fun showErrorView() {
        binding.nsv.hideView()
        binding.appbar.hideView()
        binding.errorLayout.showView()
        binding.tryAgainText.setOnClickListener {
            viewModel.getMovieDetails(movieId)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieDetails(data: Movie) {
        binding.appbar.showView()
        binding.nsv.showView()
        item = data
        binding.imgPoster.loadImage(BuildConfig.imageUrl + data.backdropPath)
        binding.tvTitle.text = data.title
        binding.tvOverview.text = data.overview
        binding.tvTagline.text = data.tagline
        binding.tvStatus.text = data.status
        val formatter = DecimalFormat("#,###,###")
        binding.tvRevenue.text = (formatter.format(data.revenue ?: 0)).toString() + "$"
        binding.tvYear.text = data.releaseDate.toDate()
    }

    private fun setSimilarMovies(data: MoviesResponse) {
        val list = if (data.results.size > 5) data.results.subList(0, 5).toList()
        else data.results
        val adapter = SimilarMoviesAdapter(list as ArrayList<Movie>)
        binding.rvSimilars.adapter = adapter
    }

    private fun setCast(data: CreditLists) {
        val actorsList = if (data.actors.size > 5) data.actors.subList(0, 5).toList()
        else data.actors

        val actorsAdapter = CastAdapter(actorsList as ArrayList<Cast>)
        binding.rvActors.adapter = actorsAdapter

        val directorsList = if (data.directors.size > 5) data.directors.subList(0, 5).toList()
        else data.directors

        val directorsAdapter = CastAdapter(directorsList as ArrayList<Cast>)
        binding.rvDirectors.adapter = directorsAdapter

    }

    private fun setFavorite() {
        if (item?.isFavorite == true)
            binding.imgFavorite.setImageResource(R.drawable.ic_fav_active)
        else binding.imgFavorite.setImageResource(R.drawable.ic_fav)


        binding.imgFavorite.setOnClickListener {
            if (item?.isFavorite == true) {
                binding.imgFavorite.setImageResource(R.drawable.ic_fav)
                item?.id?.let { it1 -> viewModel.deleteMovie(it1) }
            } else {
                binding.imgFavorite.setImageResource(R.drawable.ic_fav_active)
                item?.let { it1 -> viewModel.saveMovie(it1) }
            }

            item?.isFavorite = !(item?.isFavorite ?: false)

            val myAnim = AnimationUtils.loadAnimation(binding.root.context, R.anim.bounce)
            val interpolator = MyBounceInterpolator(0.2, 20.0)
            myAnim.interpolator = interpolator
            binding.imgFavorite.startAnimation(myAnim)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}