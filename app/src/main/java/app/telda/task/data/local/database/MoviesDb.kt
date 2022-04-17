package app.telda.task.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.telda.task.data.remote.entities.Movie


@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConverterClass::class)
abstract class MoviesDb : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}

