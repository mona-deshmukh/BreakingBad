package com.example.breakingbad

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import retrofit2.await
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private var _loading: MutableLiveData<Boolean>? = null
    private var _characters: MutableLiveData<List<Character>>? = null
    private var _error: MutableLiveData<String>? = null
    private val parentJob = Job()

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    val characters: MutableLiveData<List<Character>>
        get() {
            if (_characters == null) {
                _characters = MutableLiveData()
            }

            return _characters!!
        }

    val loading: MutableLiveData<Boolean>
        get() {
            if (_loading == null) {
                _loading = MutableLiveData()
            }

            return _loading!!
        }

    val error: MutableLiveData<String>
        get() {
            if (_error == null) {
                _error = MutableLiveData()
            }

            return _error!!
        }

    fun getCharacters() {
        loading.postValue(true)

        viewModelScope.async {
            val result = RetrofitClient.getBreakingBadAPI().getCharacters().await()
            _characters?.postValue(result)
        }

        loading.postValue(false)
    }

}