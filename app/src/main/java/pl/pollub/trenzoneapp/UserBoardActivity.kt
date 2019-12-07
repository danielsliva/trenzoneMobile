package pl.pollub.trenzoneapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.user_board_activity.*
import pl.pollub.trenzoneapp.api.TrainingsApiData
import pl.pollub.trenzoneapp.api.TrainingsDataResponse
import pl.pollub.trenzoneapp.api.TrainingsResponse


class UserBoardActivity : AppCompatActivity() {

    private lateinit var t: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_board_activity)
        val bundle: Bundle = intent.extras
        val username = bundle.get("username").toString()
        val token = bundle.get("token").toString()

        val dl = findViewById<DrawerLayout>(R.id.user_board_activity)
        t = ActionBarDrawerToggle(this, dl, R.string.drawer_open, R.string.drawer_close)

        dl.addDrawerListener(t)
        t.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val nv = findViewById<NavigationView>(R.id.nv)

        menuButton.setOnClickListener {
            if (!dl.isDrawerOpen(Gravity.START)) {
                helloUsername.text = "Witaj $username!"
                dl.openDrawer(Gravity.START)
            }
            else dl.closeDrawer(Gravity.END)
        }

        nv.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.myAccount -> {
                    val intent = Intent(this, AccountDetailsActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                }
                R.id.myActiveTrainingsButton -> {
                    val intent = Intent(this, ActiveTrainingListActivity::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("token", token)
                    intent.putExtra("mark", "day")
                    startActivity(intent)
                }
                R.id.myTrainingsButton -> {
                    val intent = Intent(this, TrainingListActivity::class.java)
                    intent.putExtra("myTraining", true)
                    intent.putExtra("username", username)
                    intent.putExtra("token", token)
                    startActivity(intent)
                }
                R.id.myAchievements -> {
                    val intent = Intent(this, ActiveTrainingListActivity::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("token", token)
                    intent.putExtra("mark", "achieve")
                    startActivity(intent)
                }
                R.id.logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    return@setNavigationItemSelectedListener true
                }
            }
            return@setNavigationItemSelectedListener true
        }

        fullFillTrainingList()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (t.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

    private fun fullFillTrainingList() {
        TrainingsApiData.apiData(object : TrainingsApiData.Response {
            override fun data(content: TrainingsResponse, status: Boolean) {
                if (status) {
                    val trainings: List<TrainingsDataResponse> = content.content
                    val trainingListAdapter = TrainingsListAdapter(this@UserBoardActivity, trainings)
                    listView.adapter = trainingListAdapter

                    listView.setOnItemClickListener { _, _, position, _ ->

                        val intent = Intent(this@UserBoardActivity, TrainingActivity::class.java)
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
