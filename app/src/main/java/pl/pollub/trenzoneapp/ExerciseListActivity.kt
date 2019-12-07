package pl.pollub.trenzoneapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.training_list_activity.*
import pl.pollub.trenzoneapp.api.*

class ExerciseListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_list_activity)

        val bundle: Bundle = intent.extras
        val id: Long = bundle.getString("id").toLong()

        ExercisesApiData.apiDataForExercises( id, object : ExercisesApiData.Response{
            override fun data(content: List<ExerciseResponse>, status: Boolean) {
                if(status){
                    val exercises: List<ExerciseResponse> = content
                    val exerciseListAdapter = ExercisesListAdapter(this@ExerciseListActivity, exercises)
                    trainingListView.adapter = exerciseListAdapter
                }
            }
        })
    }
}
