package pl.pollub.trenzoneapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.achievement_list_activity.*
import pl.pollub.trenzoneapp.api.AchievementApiData
import pl.pollub.trenzoneapp.api.SimpleAchievement
import pl.pollub.trenzoneapp.api.SimpleAchievementImage


class AchievementListActivity : AppCompatActivity() {

    private var lv: ListView? = null
    private var adapter: AchievementListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.achievement_list_activity)
        achievement_progressBar.visibility = View.VISIBLE
        val bundle: Bundle = intent.extras
        val activeTrainingId = bundle.get("activeTrainingId") as String
        val date = bundle.get("date") as String
        val username = bundle.get("username") as String

        lv = findViewById(R.id.achievementListView)

        AchievementApiData.apiDataForAchievements(username, activeTrainingId, date, object : AchievementApiData.Response {
            override fun data(content: SimpleAchievementImage, status: Boolean) {
                if (status) {
                    val achievements: List<SimpleAchievement> = content.achievements
                    val imagePath: String = content.imagePath
                    adapter = AchievementListAdapter(this@AchievementListActivity, achievements)
                    if (!imagePath.isEmpty()) {
                        Picasso.get().load("file:$imagePath").into(pictureView)
                    } else pictureView.setImageResource(R.drawable.brakzdjecia)
                    lv!!.adapter = adapter
                    achievementText.text = "Osiągnięte wyniki z dnia $date"
                    achievement_progressBar.visibility = View.INVISIBLE

                }
            }
        })

    }

}

