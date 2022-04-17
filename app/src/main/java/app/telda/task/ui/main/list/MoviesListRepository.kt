package app.telda.task.ui.main.list


import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.telda.task.base.BaseRepository
import app.telda.task.data.local.database.MoviesDao
import app.telda.task.data.remote.apiCalls.MoviesApiCalls
import app.telda.task.data.remote.entities.Movie
import app.telda.task.ui.main.list.dataSource.MoviesListDataSource
import app.telda.task.ui.main.list.dataSource.SearchDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


class MoviesListRepository @Inject constructor (
    private val apiCalls: MoviesApiCalls,
    private val moviesDao: MoviesDao,
    @ApplicationContext val context: Context
        ) : BaseRepository() {

    private var dataSource: SearchDataSource? = null

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesListDataSource(
                    apiCalls,
                    moviesDao)
            }
        ).flow
    }

    fun search(query: String,year:Int?): Flow<PagingData<Movie>> {
        dataSource?.invalidate()

        dataSource = SearchDataSource(apiCalls, query, year,moviesDao)

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                if (dataSource == null)
                    SearchDataSource(apiCalls, query, year,moviesDao)
                else dataSource!!

            }
        ).flow

    }

    fun saveMovie(movie:Movie) : Response<Boolean> {
        moviesDao.insertMovie(movie)
        return Response.success(true)
    }

    fun deleteMovie(id: String) : Response<Boolean> {
        moviesDao.deleteMovieById(id)
        return Response.success(true)
    }

}