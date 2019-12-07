package pl.pollub.trenzoneapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import pl.pollub.trenzoneapp.api.TrainingsDataResponse

class TrainingsListAdapter(private val context: Activity, private val trainings: List<TrainingsDataResponse>)
    : ArrayAdapter<String>(context, R.layout.training_list_item, trainings.map { training -> training.name }) {

    val names: List<String> = trainings.map { trainingsDataResponse -> trainingsDataResponse.name }
    val usernames: List<String> = trainings.map { trainingsDataResponse -> trainingsDataResponse.username }
    val descriptions: List<String> = trainings.map { trainingsDataResponse -> trainingsDataResponse.description }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.training_list_item, null, true)

        val nameText = rowView.findViewById(R.id.trainingName) as TextView
        val usernameText = rowView.findViewById(R.id.username) as TextView
        val descText = rowView.findViewById(R.id.description) as TextView

        nameText.text = names[position]
        if (descriptions[position].length >= 150) {
        descText.text = descriptions[position].slice(IntRange(0,150)).plus("...") }
        else descText.text = descriptions[position]
        usernameText.text = usernames[position]

        return rowView
    }
}