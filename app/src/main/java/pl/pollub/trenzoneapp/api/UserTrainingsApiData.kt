package pl.pollub.trenzoneapp.api


import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserTrainingsApiData {
    companion object {
        val api by lazy { Connector.callUserTrainingsApi() }
        var disposable: Disposable? = null
        fun apiData(token: String, username: String, callback: Response) {
            disposable = api.getUserTrainings(username, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        callback.data(result, true)
                    }, { error ->
                        error.printStackTrace()
                    })
        }
    }

    interface Response {
        fun data(content: List<TrainingsDataResponse>, status: Boolean)
    }
}