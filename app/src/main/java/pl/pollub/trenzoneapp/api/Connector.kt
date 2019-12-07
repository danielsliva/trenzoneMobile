package pl.pollub.trenzoneapp.api

import okhttp3.OkHttpClient
import pl.pollub.trenzoneapp.auth.ServiceInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class Connector {

    companion object {
        private fun getRetrofit(Url: String): Retrofit {

            val client = OkHttpClient.Builder()
                    .addInterceptor(ServiceInterceptor())
                    .readTimeout(45, TimeUnit.SECONDS)
                    .writeTimeout(45,TimeUnit.SECONDS)
                    .build()

            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Url)
                    .client(client)
                    .build()
        }

        private fun getApiData(): Retrofit {
            return getRetrofit(Url.BASE_URL)
        }

        fun callTrainingsApi(): TrainingApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(TrainingApi::class.java)
        }

        fun callUserTrainingsApi(): UserTrainingApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(UserTrainingApi::class.java)
        }

        fun callActiveTrainingsApi(): ActiveTrainingApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(ActiveTrainingApi::class.java)
        }

        fun callExercisesApi(): ExercisesApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(ExercisesApi::class.java)
        }

        fun callLoginApi(): LoginApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(LoginApi::class.java)
        }

        fun callAchievementApi(): AchievementApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(AchievementApi::class.java)
        }

        fun callAccountApi(): AccountApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(AccountApi::class.java)
        }
    }
}