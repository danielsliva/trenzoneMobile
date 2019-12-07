package pl.pollub.trenzoneapp.api

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {
    @POST("/auth/signin")
    fun login(@Body value: LoginModel): Observable<LoginResponse>
}

interface TrainingApi {
    @GET(Url.TRAININGS_URL)
    fun getTrainings(): Observable<TrainingsResponse>
}

interface UserTrainingApi {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/account/{username}/trainings")
    fun getUserTrainings(@Path("username") username: String, @Header("Authorization") authHeader: String): Observable<List<TrainingsDataResponse>>
}

interface ExercisesApi {
    @GET("/training/{id}/exercises")
    fun getExercisesForTraining(@Path("id") id: Long): Observable<List<ExerciseResponse>>

    @GET("/training/{id}/exercises/{day}")
    fun getExercisesForTrainingAndForDay(@Path("id") trainingId: String, @Path("day") day: String): Observable<List<ExerciseResponse>>
}

interface ActiveTrainingApi {
    @GET("/account/{username}/actives/mobile/{mark}")
    fun getActiveTraining(@Path("username") username: String, @Path("mark") mark: String): Observable<ActiveTrainingsResponse>
}

interface AchievementApi {
    @POST("/account/{username}/achievements/mobile")
    fun saveAchievements(@Body achievements: AchievementImage ,@Path("username") username: String): Observable<Response<Void>>

    @GET("/account/{username}/actives/{id}/achievements/{date}")
    fun getAchievements(@Path("username") username: String,
                        @Path("id") id: String,
                        @Path("date") date: String): Observable<List<Achievement>>
}

interface AccountApi {
    @GET("/accounts/{username}")
    fun getAccountDetails(@Path("username") username: String): Observable<AccountDetails>
}