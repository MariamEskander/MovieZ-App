package app.telda.task.ui.main.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.telda.task.data.remote.entities.Movie
import app.telda.task.databinding.FragmentWishListBinding
import app.telda.task.utils.Status
import app.telda.task.utils.extensions.hideView
import app.telda.task.utils.extensions.observe
import app.telda.task.utils.extensions.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment(), WishListAdapter.SetMovieClickListener {

    private var items: ArrayList<Movie>? = null
    private val viewModel: WishlistViewModel by viewModels()

    private var _binding: FragmentWishListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObservers()
    }

    private fun initView() {
        viewModel.getMovies()
    }

    private fun setObservers() {
        observe(viewModel.wishListStatus) { status ->
            when (status) {
                is Status.Loading -> {
                }
                is Status.Success<*> -> {
                    val data = status.data as List<Movie>?
                    setWishList(data)
                }
                else -> {

                }
            }
        }
    }

    private fun setWishList(data: List<Movie>?) {
        if (data.isNullOrEmpty())
            binding.noDataLayout.showView()
        else {
            items = data as ArrayList<Movie>?
            binding.noDataLayout.hideView()

            val adapter = WishListAdapter(data, this)
            binding.rv.adapter = adapter
        }
    }

    override fun changeFavoriteStatus(item: Movie) {
        viewModel.deleteMovie(item.id)
        items?.remove(item)
        if (items.isNullOrEmpty())
            binding.noDataLayout.showView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}