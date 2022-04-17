package app.telda.task.ui.main.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.telda.task.BuildConfig
import app.telda.task.data.remote.entities.Movie
import app.telda.task.data.remote.entities.MoviesResponse
import app.telda.task.databinding.FragmentMovieDetailsBinding
import app.telda.task.ui.main.details.adapters.SimilarMoviesAdapter
import app.telda.task.utils.Status
import app.telda.task.utils.extensions.loadImage
import app.telda.task.utils.extensions.observe
import app.telda.task.utils.extensions.toDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(){

    private var movieId: String = ""
    private val viewModel: MovieDetailsViewModel by viewModels()

    private var _binding: FragmentMovieDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObservers()
    }

    private fun initView() {
        arguments?.let {
            movieId = arguments?.getString("movieId") ?: ""
            viewModel.getMovieDetails(movieId)
            viewModel.getSimilarMovies(movieId)
        }

    }

    private fun setObservers() {
        observe(viewModel.detailsStatus) { status ->
            when (status) {
                is Status.Loading -> {
                }
                is Status.Success<*> -> {
                    val data = status.data as Movie
                    setMovieDetails(data)

                }
                else -> {

                }
            }
        }

        observe(viewModel.similarStatus) { status ->
            when (status) {
                is Status.Loading -> {
                }
                is Status.Success<*> -> {
                    val data = status.data as MoviesResponse
                    setSimilarMovies(data)
                }
                else -> {

                }
            }
        }
    }

    private fun setSimilarMovies(data: MoviesResponse) {
       val list = if (data.results.size > 5) data.results.subList(0,5).toList()
        else data.results
        val adapter = SimilarMoviesAdapter(list as ArrayList<Movie>)
        binding.rvSimilars.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieDetails(data: Movie) {
        binding.imgPoster.loadImage(BuildConfig.imageUrl+data.backdropPath)
        binding.tvTitle.text = data.title
        binding.tvOverview.text = data.overview
        binding.tvTagline.text = data.tagline
        binding.tvStatus.text = data.status
        binding.tvRevenue.text = (data.revenue?:0.0).toString() + "$"
        binding.tvYear.text = data.releaseDate.toDate()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}