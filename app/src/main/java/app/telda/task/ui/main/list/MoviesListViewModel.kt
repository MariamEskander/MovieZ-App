package app.telda.task.ui.main.list


import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.telda.task.base.BaseViewModel
import app.telda.task.data.remote.entities.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: MoviesListRepository
) : BaseViewModel(repository){

    var listData: Flow<PagingData<Movie>> = flowOf()
    var searchData: Flow<PagingData<Movie>> = flowOf()

    fun search(query:String,year:Int?) {
        isNetworkConnected()
        listData =
            repository.search(query,year).flowOn(Dispatchers.Main).cachedIn(viewModelScope)
    }
    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        isNetworkConnected()
        listData =
            repository.getPopularMovies().flowOn(Dispatchers.Main).cachedIn(viewModelScope)
    }
}