package app.telda.task.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.telda.task.data.remote.entities.Movie


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(item: Movie)

    @Query("SELECT * FROM Movie WHERE id = :id ")
    fun getMovieById(id: String): Movie?

    @Query("DELETE FROM Movie WHERE id = :id")
    fun deleteMovieById(id: String)

    @Query("SELECT * FROM Movie")
    fun getMovies(): List<Movie>?


}