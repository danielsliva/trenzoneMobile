package pl.pollub.trenzoneapp.api

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ActiveTrainingsApiData {
    companion object {
        val api by lazy { Connector.callActiveTrainingsApi() }
        var disposable: Disposable? = null
        fun apiData(mark: String, username: String, callback: Response) {
            disposable = api.getActiveTraining(username, mark)
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
        fun data(content: ActiveTrainingsResponse, status: Boolean)
    }
}