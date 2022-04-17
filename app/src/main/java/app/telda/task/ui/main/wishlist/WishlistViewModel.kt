package app.telda.task.ui.main.wishlist


import app.telda.task.base.BaseViewModel
import app.telda.task.utils.SingleLiveEvent
import app.telda.task.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WishlistViewModel @Inject constructor(private val repository: WishlistRepository) :
    BaseViewModel(repository) {
    val wishListStatus = SingleLiveEvent<Status>()
    private val favStatus = SingleLiveEvent<Status>()

    fun getMovies() {
        performNetworkCall(
            { repository.getMovies() }, wishListStatus , isDatabase = true
        )
    }

    fun deleteMovie(id: String) {
        performNetworkCall(
            { repository.deleteMovie(id) }, favStatus , isDatabase = true
        )
    }

}