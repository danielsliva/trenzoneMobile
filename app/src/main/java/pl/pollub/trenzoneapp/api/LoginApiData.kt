package pl.pollub.trenzoneapp.api


import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class LoginApiData {
    companion object {
        val api by lazy { Connector.callLoginApi() }
        var disposable: Disposable? = null
        fun apiData(username: String, password: String, callback: Response) {
            disposable = api.login(LoginModel(username, password))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        callback.data(result, true)
                    }, { error ->
                        callback.data(null, false)
                    })
        }
    }

    interface Response {
        fun data(content: LoginResponse?, status: Boolean)
    }
}