package pl.pollub.trenzoneapp.api


import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TrainingsApiData {
    companion object {
        val api by lazy { Connector.callTrainingsApi() }
        var disposable: Disposable? = null
        fun apiData(callback: Response) {
            disposable = api.getTrainings()
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
        fun data(content: TrainingsResponse, status: Boolean)
    }
}