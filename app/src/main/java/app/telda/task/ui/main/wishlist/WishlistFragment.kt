package app.telda.task.ui.main.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.telda.task.base.BaseFragment
import app.telda.task.base.BaseViewModel
import app.telda.task.data.remote.entities.Movie
import app.telda.task.databinding.FragmentWishListBinding
import app.telda.task.utils.Status
import app.telda.task.utils.extensions.hideView
import app.telda.task.utils.extensions.observe
import app.telda.task.utils.extensions.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : BaseFragment(), WishListAdapter.SetMovieClickListener {

    private var items: ArrayList<Movie>? = null
    private val viewModel: WishlistViewModel by viewModels()
    override fun getViewModel(): BaseViewModel = viewModel
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

    override fun onViewReady() {
        viewModel.getMovies()
    }

    override fun setObservers() {
        observe(viewModel.wishListStatus) { status ->
            when (status) {
                is Status.Success<*> -> {
                    setWishList(status.data as List<Movie>?)
                }
                else -> {}
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