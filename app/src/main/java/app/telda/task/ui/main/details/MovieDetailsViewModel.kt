package app.telda.task.ui.main.details


import androidx.lifecycle.viewModelScope
import app.telda.task.base.BaseViewModel
import app.telda.task.data.remote.entities.Cast
import app.telda.task.data.remote.entities.CreditLists
import app.telda.task.data.remote.entities.Movie
import app.telda.task.utils.Constants.ACTORS
import app.telda.task.utils.Constants.DIRECTORS
import app.telda.task.utils.SingleLiveEvent
import app.telda.task.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieDetailsRepository) :
    BaseViewModel(repository) {
    val detailsStatus = SingleLiveEvent<Status>()
    val similarStatus = SingleLiveEvent<Status>()
    private val creditStatus = SingleLiveEvent<Status>()
    val creditDataStatus = SingleLiveEvent<Status>()
    private val actors = ArrayList<Cast>()
    private val directors = ArrayList<Cast>()

    fun getMovieDetails(id: String) {
        performNetworkCall(
            { repository.getMovieDetails(id) }, detailsStatus
        )
    }

    fun getSimilarMovies(id: String) {
        performNetworkCall(
            { repository.getSimilarMovies(id) }, similarStatus, doOnSuccess = {
                if (it != null)
                    viewModelScope.launch {
                        val list = if (it.results.size > 5) it.results.subList(0, 5).toList()
                        else it.results
                        getAllCredits(list as ArrayList<Movie>)
                    }
            }
        )
    }

    private suspend fun getAllCredits(list: ArrayList<Movie>) = coroutineScope {
        awaitAll({
            for (i in list)
                getMovieCredits(i.id,i==list.last())
        })
    }

    private suspend fun awaitAll(vararg blocks: suspend () -> Unit) = coroutineScope {
        blocks.forEach {
            launch { it() }
        }
    }

    private suspend fun getMovieCredits(id: String, isLast: Boolean) {
        performNetworkCall(
            { repository.getMovieCredits(id) }, creditStatus, doOnSuccess = {
                if (it != null) {
                    for (i in it.crew) {
                       checkItem(i)
                    }
                    for (i in it.cast) {
                        checkItem(i)
                    }
                    if (isLast) {
                        actors.sortByDescending { actor -> actor.popularity }
                        directors.sortByDescending { director -> director.popularity }
                        creditDataStatus.postValue(Status.Success(CreditLists(actors, directors )))
                    }
                }
            }
        )
    }

    private fun checkItem(i: Cast) {
        if (i.department == ACTORS && !actors.contains(i))
            actors.add(i)
        if (i.department == DIRECTORS && !directors.contains(i))
            directors.add(i)
    }

}