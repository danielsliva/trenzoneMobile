package pl.pollub.trenzoneapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ListView
import kotlinx.android.synthetic.main.exercise_use_list_activity.*
import pl.pollub.trenzoneapp.api.ExerciseResponse
import pl.pollub.trenzoneapp.api.ExercisesApiData


class ExerciseUseListActivity : AppCompatActivity() {

    private var btn: Button? = null
    private var lv: ListView? = null
    private var useListAdapter: ExercisesUseListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_use_list_activity)
        exercise_progressBar.visibility = View.VISIBLE
        val bundle: Bundle = intent.extras
        val token = bundle.get("token") as String
        val trainingId = bundle.get("trainingId") as String
        val activeTrainingId = bundle.get("activeTrainingId") as String
        val trainingName = bundle.get("trainingName") as String
        val day = bundle.get("day") as String
        val username = bundle.get("username") as String

        lv = findViewById(R.id.trainingListView)
        btn = findViewById(R.id.btn)


        ExercisesApiData.apiDataForExercisesForDay(trainingId, day, object : ExercisesApiData.Response {
            override fun data(content: List<ExerciseResponse>, status: Boolean) {
                if (status) {
                    val exercise: List<ExerciseResponse> = content
                    useListAdapter = ExercisesUseListAdapter(this@ExerciseUseListActivity, exercise)
                    lv!!.adapter = useListAdapter
                    btn!!.setOnClickListener {
                        val intent = Intent(this@ExerciseUseListActivity, SummarizeActivity::class.java)
                        intent.putExtra("activeTrainingId", activeTrainingId)
                        intent.putExtra("trainingName", trainingName)
                        intent.putExtra("username", username)
                        intent.putExtra("token", token)
                        startActivity(intent)
                    }
                    exercise_progressBar.visibility = View.INVISIBLE
                    btn!!.visibility = View.VISIBLE


                }
            }
        })

    }

}

