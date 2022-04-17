package app.telda.task.ui.main.details


import app.telda.task.base.BaseViewModel
import app.telda.task.utils.SingleLiveEvent
import app.telda.task.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieDetailsRepository) : BaseViewModel(repository){
    val detailsStatus = SingleLiveEvent<Status>()
    val similarStatus = SingleLiveEvent<Status>()


    fun getMovieDetails(id: String) {
        performNetworkCall(
            { repository.getMovieDetails(id) }, detailsStatus
        )
    }

    fun getSimilarMovies(id: String) {
        performNetworkCall(
            { repository.getSimilarMovies(id) }, similarStatus
        )
    }
}