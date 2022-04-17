package app.telda.task.ui.main.details


import app.telda.task.base.BaseRepository
import app.telda.task.data.remote.apiCalls.MoviesApiCalls
import javax.inject.Inject


class MovieDetailsRepository @Inject constructor (private val apiCalls: MoviesApiCalls) : BaseRepository(){

    suspend fun getMovieDetails(id:String) = apiCalls.getMovieDetails(id)
    suspend fun getSimilarMovies(id:String) = apiCalls.getSimilarMovies(id)

}