package app.telda.task.data.remote.entities

import com.google.gson.annotations.SerializedName

data class CreditResponse(
    @SerializedName("id") var id: String,
    @SerializedName("cast") var cast: ArrayList<Cast>,
    @SerializedName("crew") var crew: ArrayList<Cast>
)

data class Cast(
    @SerializedName("id") var id: String,
    @SerializedName("known_for_department") var department: String,
    @SerializedName("name") var name: String,
    @SerializedName("profile_path") var profile: String?,
    @SerializedName("popularity") var popularity: Double
)

data class CreditLists(
    var actors: ArrayList<Cast>,
    var directors: ArrayList<Cast>
)