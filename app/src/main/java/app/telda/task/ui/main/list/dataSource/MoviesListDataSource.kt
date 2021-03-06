package app.telda.task.ui.main.list.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.telda.task.data.remote.apiCalls.MoviesApiCalls
import app.telda.task.data.remote.entities.Movie
import retrofit2.HttpException
import java.io.IOException



private const val STARTING_PAGE_INDEX = 1

class MoviesListDataSource(
    private val apiCalls: MoviesApiCalls
    ) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return STARTING_PAGE_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiCalls.getPopularMovies(page)
            if (response.body()?.results == null && response.errorBody() != null) {
                return LoadResult.Error(IOException(response.errorBody().toString()))
            } else {
                val list = response.body()?.results ?: listOf()
                LoadResult.Page(
                    data = list,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page,
                    nextKey = if (list.isEmpty()) null else (page + 1)
                )
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}