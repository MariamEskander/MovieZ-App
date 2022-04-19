package app.telda.task.data.local.database

import androidx.room.TypeConverter
import app.telda.task.data.remote.entities.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ConverterClass {

    @TypeConverter
    fun fromMovie(data: Movie?): String? {
        if (data == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Movie>() {

        }.type
        return gson.toJson(data, type)
    }

    @TypeConverter
    fun toMovie(dataString: String?): Movie? {
        if (dataString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Movie>() {

        }.type
        return gson.fromJson<Movie>(dataString, type)
    }


}