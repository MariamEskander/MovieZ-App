package app.telda.task.ui.main.list

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import app.telda.task.R
import app.telda.task.base.BaseFragment
import app.telda.task.base.BaseViewModel
import app.telda.task.data.remote.entities.Movie
import app.telda.task.databinding.FragmentMoviesListBinding
import app.telda.task.ui.main.list.adapter.MoviesPagedListAdapter
import app.telda.task.ui.main.list.adapter.SetMovieClickListener
import app.telda.task.utils.PagingLoadingStateAdapter
import app.telda.task.utils.extensions.hideView
import app.telda.task.utils.extensions.showView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class MoviesListFragment : BaseFragment(), SetMovieClickListener {

    private var calendar: Calendar? = null
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private val adapter: MoviesPagedListAdapter by lazy {
        MoviesPagedListAdapter(this)
    }

    private val viewModel: MoviesListViewModel by viewModels()
    override fun getViewModel(): BaseViewModel = viewModel

    private var _binding: FragmentMoviesListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewReady() {
        calendar = Calendar.getInstance()
        month = calendar!!.get(Calendar.MONTH)
        day = calendar!!.get(Calendar.DAY_OF_MONTH)

        setClickListeners()
        setSearchEdittext()
        setMoviesListAdapter()
    }

    private fun setClickListeners() {
        binding.imgClear.setOnClickListener {
            year = 0
            binding.etSearch.setText("")
        }

        binding.imgWishlist.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_wishlist)
        }

        binding.imgDate.setOnClickListener {
            showDatePicker()
        }

        binding.tryAgainText.setOnClickListener {
            adapter.refresh()
        }
    }

    private fun setSearchEdittext() {
        binding.etSearch.addTextChangedListener {
            doRequest()
            setObservers()
        }
    }

    private fun doRequest() {
        if (binding.etSearch.text.isNullOrEmpty()) {
            viewModel.getPopularMovies()
            binding.imgClear.hideView()
        } else {
            viewModel.search(binding.etSearch.text.trim().toString(), year)
            binding.imgClear.showView()
        }
    }

    private fun setMoviesListAdapter() {
        binding.rv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PagingLoadingStateAdapter { adapter.retry() },
            footer = PagingLoadingStateAdapter { adapter.retry() },
        )

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    showDialogLoading()
                    binding.noDataLayout.hideView()
                    binding.rv.hideView()
                    binding.errorLayout.hideView()
                }else hideDialogLoading()
                if (loadState.refresh is LoadState.Error) {
                    showErrorView()
                } else if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    showEmptyView()
                } else if (loadState.source.refresh is LoadState.NotLoading && adapter.itemCount > 0) {
                    showRecyclerView()
                }
            }
        }
    }

    override fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listData.collectLatest { data ->
                adapter.submitData(viewLifecycleOwner.lifecycle, data)
            }
            viewModel.searchData.collectLatest { data ->
                adapter.submitData(viewLifecycleOwner.lifecycle, data)
            }
        }
    }

    private fun showDatePicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { _, p1, _, _ ->
                year = p1
                if (binding.etSearch.text.trim().toString().isNotEmpty())
                    viewModel.search(binding.etSearch.text.trim().toString(), year)
            }, if (year == 0) calendar!!.get(Calendar.YEAR) else year, month, day
        )
        picker.datePicker.findViewById<Spinner>(
            resources.getIdentifier("day", "id", "android")
        ).hideView()
        picker.datePicker.findViewById<Spinner>(
            resources.getIdentifier("month", "id", "android")
        ).hideView()
        picker.show()

    }

    private fun showErrorView() {
            binding.errorLayout.showView()
    }

    private fun showEmptyView() {
        if (adapter.itemCount == 0)
            binding.noDataLayout.showView()
    }

    private fun showRecyclerView() {
        lifecycleScope.launch {
            binding.noDataLayout.hideView()
            binding.rv.showView()
            binding.errorLayout.hideView()
        }
    }

    override fun onMovieClicked(item: Movie, position: Int) {
        val bundle = Bundle()
        bundle.putString("movieId", item.id)
        findNavController().navigate(R.id.action_list_to_details, bundle)
    }

    override fun changeFavoriteStatus(item: Movie, isFavorite: Boolean) {
        viewModel.changeFavoriteStatus(item, isFavorite)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
