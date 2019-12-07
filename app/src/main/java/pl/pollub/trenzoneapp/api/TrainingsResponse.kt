package pl.pollub.trenzoneapp.api

class TrainingsResponse(val content: List<TrainingsDataResponse>)

class ActiveTrainingsResponse(
        val map: Map<String, List<String>>,
        val ids: List<String>,
        val activeIds: List<String>
)

class TrainingsDataResponse(
        val id: String,
        val name: String,
        val username: String,
        val description: String,
        val date: String,
        val rate: String,
        val difficulty: String
)

class AchievementImage(
        val achievements: List<Achievement>,
        val imagePath: String
)

class SimpleAchievementImage(
        val achievements: List<SimpleAchievement>,
        val imagePath: String
)

class Achievement(
        val exerciseName: String,
        val sequence: String,
        val unit: String,
        val activeTrainingId: String,
        val trainingName: String,
        val previousValue: String,
        val progressValue: String
)

class SimpleAchievement(
        val exerciseName: String,
        val previousValue: String,
        val progressValue: String
)


class ExerciseResponse(
        val name: String,
        val unit: String,
        val day: String,
        val quantity: String,
        val series: String,
        val sequence: String,
        var editText: String,
        var checkBox: Boolean
)

class LoginModel(
        val username: String,
        val password: String
)

class LoginResponse(
        val username: String,
        val type: String,
        val token: String
)

class AccountDetails(
        val name: String,
        val lastName: String,
        val username: String,
        val email: String,
        val age: String,
        val height: String,
        val weight: String,
        val trainerDetails: TrainerDetails
)

class TrainerDetails(
        val description: String,
        val phoneNumber: String,
        val city: String,
        val job: String
)