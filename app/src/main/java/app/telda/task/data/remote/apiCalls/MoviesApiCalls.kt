package app.telda.task.data.remote.apiCalls


import app.telda.task.BuildConfig
import app.telda.task.data.remote.apis.MoviesApis


class MoviesApiCalls(private val apis: MoviesApis) {
     suspend fun getPopularMovies(page:Int) = apis.getPopularMovies(BuildConfig.apiKey,page)
     suspend fun search(page:Int,query:String,year:Int?) = apis.search(BuildConfig.apiKey,page,query, year)
}