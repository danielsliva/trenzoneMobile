package pl.pollub.trenzoneapp.api


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class AchievementApiData {
    companion object {
        val api by lazy { Connector.callAchievementApi() }
        var disposable: Disposable? = null
        fun apiData(achievementImage: AchievementImage, username: String, callback: EmptyResponse) {
            disposable = api.saveAchievements(achievementImage, username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        callback.data(true)
                    }, { error ->
                        error.printStackTrace()
                    })
        }

        fun apiDataForAchievements(username: String, id: String, date: String, callback: Response) {
            disposable = api.getAchievements(username, id, date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        callback.data(result, true)
                    }, { error ->
                        error.printStackTrace()
                    })
        }
    }

    interface EmptyResponse {
        fun data(status: Boolean)
    }

    interface Response {
        fun data(content: SimpleAchievementImage, status: Boolean)
    }
}