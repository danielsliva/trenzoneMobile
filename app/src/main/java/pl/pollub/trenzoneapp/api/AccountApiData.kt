package pl.pollub.trenzoneapp.api

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AccountApiData {
    companion object {
        val api by lazy { Connector.callAccountApi() }
        var disposable: Disposable? = null
        fun apiData(username: String, callback: Response) {
            disposable = api.getAccountDetails(username)
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
        fun data(content: AccountDetails, status: Boolean)
    }
}