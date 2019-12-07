package pl.pollub.trenzoneapp.api


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ExercisesApiData {
    companion object {
        val api by lazy { Connector.callExercisesApi() }
        var disposable: Disposable? = null
        fun apiDataForExercises(id: Long, callback: Response) {
            disposable = api.getExercisesForTraining(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        callback.data(result, true)
                    }, { error ->
                        error.printStackTrace()
                    })
        }

        fun apiDataForExercisesForDay(trainingId: String, day: String,  callback: Response) {
            disposable = api.getExercisesForTrainingAndForDay(trainingId, day)
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
        fun data(content: List<ExerciseResponse>, status: Boolean)
    }
}