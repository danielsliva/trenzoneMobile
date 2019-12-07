package pl.pollub.trenzoneapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.training_list_activity.*
import pl.pollub.trenzoneapp.api.TrainingsDataResponse
import pl.pollub.trenzoneapp.api.UserTrainingsApiData

class TrainingListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.training_list_activity)
        val bundle: Bundle = intent.extras
        val myTraining: Boolean = bundle.get("myTraining") as Boolean
        var username: String = ""
        var token: String = ""
        if(bundle.get("username") != null){
            username = bundle.get("username").toString()}
        if(bundle.get("token") != null){
            token = "Bearer " + bundle.get("token").toString()}

        val mSwipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swiperefresh_items)

        mSwipeRefreshLayout.setOnRefreshListener {
            if (myTraining) fullFillUserTrainingList(username, token) else null
            val handler = Handler()
            handler.postDelayed({
                if (mSwipeRefreshLayout.isRefreshing) {
                    mSwipeRefreshLayout.isRefreshing = false
                }
            }, 1000)
        }

        if (myTraining) fullFillUserTrainingList(username, token) else null

    }

    private fun fullFillUserTrainingList(username: String, token: String) {
        UserTrainingsApiData.apiData(token, username ,object : UserTrainingsApiData.Response {
            override fun data(content: List<TrainingsDataResponse>, status: Boolean) {
                if (status) {
                    val trainings: List<TrainingsDataResponse> = content
                    val trainingListAdapter = TrainingsListAdapter(this@TrainingListActivity, trainings)
                    trainingListView.adapter = trainingListAdapter

                    trainingListView.setOnItemClickListener { adapterView, view, position, id ->

                        val intent = Intent(this@TrainingListActivity, TrainingActivity::class.java)
                        intent.putExtra("id", trainings[position].id)
                        intent.putExtra("name", trainings[position].name)
                        intent.putExtra("username", trainings[position].username)
                        intent.putExtra("desc", trainings[position].description)
                        intent.putExtra("date", trainings[position].date)
                        intent.putExtra("diff", trainings[position].difficulty)
                        intent.putExtra("rate", trainings[position].rate)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}
