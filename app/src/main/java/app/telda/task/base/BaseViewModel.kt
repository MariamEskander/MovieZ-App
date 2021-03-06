package app.telda.task.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.telda.task.data.remote.entities.BaseResponse
import app.telda.task.utils.ERRORS
import app.telda.task.utils.SingleLiveEvent
import app.telda.task.utils.Status
import app.telda.task.utils.extensions.toObjectFromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


abstract class BaseViewModel (private val repository: BaseRepository) : ViewModel() {
    val showNetworkError = SingleLiveEvent<Boolean>()


    fun <D> performNetworkCall(
        apiCall: suspend () -> Response<D>,
        status: SingleLiveEvent<Status>,
        doOnSuccess: (responseData: D?) -> Unit = {},
        isDatabase: Boolean? = false
    ) {

        if (isDatabase == true) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = apiCall.invoke()
                    status.postValue(Status.Success(response.body()))
                }
            }
        } else {
        if (isNetworkConnected()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        status.postValue(Status.Loading)
                        val response = apiCall.invoke()
                        when {
                            response.code() in 200..300 -> {
                                doOnSuccess(response.body())
                                status.postValue(Status.Success(response.body()))
                            }
                            response.code() == 401 -> {
                                status.postValue(
                                    Status.Error(
                                        errorCode = ERRORS.UN_AUTHORIZED
                                    )
                                )
                            }
                            else -> {
                                val error =
                                    response.errorBody()?.string()
                                        .toObjectFromJson<BaseResponse<Any?>>(BaseResponse::class.java)
                                status.postValue(
                                    Status.Error(
                                        response.code(),
                                        errorCode = ERRORS.UN_EXPECTED,
                                        errors = error.errors
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        status.postValue(
                            Status.Error(
                                errorCode = ERRORS.UNKNOWN,
                            )
                        )
                    }
                }
            }
        } else
            status.postValue(
                Status.Error(
                    errorCode = ERRORS.NO_INTRERNET,
                )
            )
    }
    }

    fun doInBackground(error: (e: Exception) -> Unit = {}, block: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    block.invoke()
                } catch (e: Exception) {
                    error.invoke(e)
                }
            }
        }
    }

    fun isNetworkConnected(): Boolean {
        val isNetworkConnected = repository.isNetworkConnected()
        if (!isNetworkConnected)
            this.showNetworkError.postValue(true)
        return isNetworkConnected
    }


}