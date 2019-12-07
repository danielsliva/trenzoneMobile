package pl.pollub.trenzoneapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import pl.pollub.trenzoneapp.api.SimpleAchievement

class AchievementListAdapter(private val context: Activity,
                             private val achievements: List<SimpleAchievement>)
    : ArrayAdapter<String>(context, R.layout.achievement_list_item, achievements.map { achievement -> achievement.exerciseName }) {

    val names: List<String> = achievements.map { exerciseResponse -> exerciseResponse.exerciseName }
    val previousValues: List<String> = achievements.map { exerciseResponse -> exerciseResponse.previousValue }
    val progressValues: List<String> = achievements.map { exerciseResponse -> exerciseResponse.progressValue }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.achievement_list_item, null, true)

        val achievementName = rowView.findViewById(R.id.achievementName) as TextView
        val previouss = rowView.findViewById(R.id.previouss) as TextView
        val progress = rowView.findViewById(R.id.progress) as TextView

        achievementName.text = names[position]
        previouss.text = previousValues[position]
        progress.text = progressValues[position]

        return rowView
    }
}