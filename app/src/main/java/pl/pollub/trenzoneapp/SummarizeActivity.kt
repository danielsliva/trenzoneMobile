package pl.pollub.trenzoneapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.achievement_list_activity.*
import kotlinx.android.synthetic.main.summarize_activity.*
import pl.pollub.trenzoneapp.api.Achievement
import pl.pollub.trenzoneapp.api.AchievementApiData
import pl.pollub.trenzoneapp.api.AchievementImage
import pl.pollub.trenzoneapp.api.ExerciseResponse
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class SummarizeActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_TAKE_PHOTO = 1
    var currentPhotoPath: String = ""
    private val PERMISSION_REQUEST_CODE: Int = 101
    private var angle: Float = 0F

    lateinit var achievements: List<Achievement>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.summarize_activity)

        val bundle: Bundle = intent.extras
        val activeTrainingId = bundle.get("activeTrainingId") as String
        val trainingName = bundle.get("trainingName") as String
        val username = bundle.get("username") as String
        val token = bundle.get("token") as String

        achievements = ExercisesUseListAdapter.exercises
                .map { exercise -> mapExerciseToAchievement(exercise, activeTrainingId, trainingName) }

        imageButton.setOnClickListener {
            if (checkPersmission()) takePicture() else requestPermission()
        }

        sendButton.setOnClickListener {
            read(username, token)
        }

    }

    private fun mapExerciseToAchievement(exercise: ExerciseResponse, activeTrainingId: String, trainingName: String): Achievement {
        return Achievement(exercise.name, exercise.sequence, exercise.unit, activeTrainingId,
                trainingName, exercise.quantity, exercise.editText)
    }

    private fun read(username: String, token: String) {

        val achievementImage: AchievementImage = AchievementImage(achievements, currentPhotoPath)

        AchievementApiData.apiData(achievementImage, username, object: AchievementApiData.EmptyResponse {
            override fun data(status: Boolean) {
                if (status) {
                    Toast.makeText(this@SummarizeActivity, "Zapisano osiągnięcia", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SummarizeActivity, UserBoardActivity::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("token", token)
                    startActivity(intent)
                }
            }


        })
    }

    private fun write(achievements: List<Achievement>) {
        achievements.forEach { achievement ->
            Log.e("exercisName", achievement.exerciseName)
            Log.e("sequence", achievement.sequence)
            Log.e("unit", achievement.unit)
            Log.e("activeTrainingId", achievement.activeTrainingId)
            Log.e("trainingName", achievement.trainingName)
            Log.e("previousValue", achievement.previousValue)
            Log.e("progressValue", achievement.progressValue)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    takePicture()

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.example.android.fileprovider",
                            photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            //To get the File for further usage
            val auxFile = File(currentPhotoPath)

            Picasso.get().load("file:$currentPhotoPath").into(imageView)

        }
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
    }

    @Throws(IOException::class)
    private fun createFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
}