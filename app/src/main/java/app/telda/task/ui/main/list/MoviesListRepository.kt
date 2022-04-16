package app.telda.task.ui.main.list


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.telda.task.base.BaseRepository
import app.telda.task.data.remote.apiCalls.MoviesApiCalls
import app.telda.task.data.remote.entities.Movie
import app.telda.task.ui.main.list.dataSource.MoviesListDataSource
import app.telda.task.ui.main.list.dataSource.SearchDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MoviesListRepository @Inject constructor (
    private val apiCalls: MoviesApiCalls
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
                    apiCalls
                )
            }
        ).flow
    }

    fun search(query: String,year:Int?): Flow<PagingData<Movie>> {
        dataSource?.invalidate()

        dataSource = SearchDataSource(apiCalls, query, year)

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                if (dataSource == null)
                    SearchDataSource(apiCalls, query, year)
                else dataSource!!
            }
        ).flow
    }

}