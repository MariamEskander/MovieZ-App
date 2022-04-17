package app.telda.task.di

import android.content.Context
import androidx.room.Room
import app.telda.task.data.local.database.MoviesDb
import app.telda.task.utils.ConnectivityUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideConnectivityUtils(@ApplicationContext context: Context): ConnectivityUtils {
        return ConnectivityUtils(context)
    }

    @Provides
    @Singleton
    fun provideMoviesDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MoviesDb::class.java, "Movies.db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideMoviesDao(db: MoviesDb) = db.moviesDao()

}

