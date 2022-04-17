package app.telda.task.ui.main.list


import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.telda.task.base.BaseViewModel
import app.telda.task.data.remote.entities.Movie
import app.telda.task.utils.SingleLiveEvent
import app.telda.task.utils.Status
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
    private val favStatus = SingleLiveEvent<Status>()

    init {
        getPopularMovies()
    }

    fun search(query:String,year:Int?) {
        isNetworkConnected()
        listData =
            repository.search(query,year).flowOn(Dispatchers.Main).cachedIn(viewModelScope)
    }

    fun getPopularMovies() {
        isNetworkConnected()
        listData =
            repository.getPopularMovies().flowOn(Dispatchers.Main).cachedIn(viewModelScope)
    }

    fun changeFavoriteStatus(item: Movie, favorite: Boolean) {
        if (favorite) saveMovie(item)
        else deleteMovie(item.id)
    }

    private fun saveMovie(movie:Movie) {
        performNetworkCall(
            { repository.saveMovie(movie) }, favStatus , isDatabase = true
        )
    }

    private fun deleteMovie(id: String) {
        performNetworkCall(
            { repository.deleteMovie(id) }, favStatus , isDatabase = true
        )
    }
}