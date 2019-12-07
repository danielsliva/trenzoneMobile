package pl.pollub.trenzoneapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import pl.pollub.trenzoneapp.api.ExerciseResponse

class ExercisesListAdapter(private val context: Activity, private val exercises: List<ExerciseResponse>)
    : ArrayAdapter<String>(context, R.layout.exercise_list_item, exercises.map { exercise -> exercise.name }) {

    val names: List<String> = exercises.map { exerciseResponse -> exerciseResponse.name }
    val days: List<String> = exercises.map { exerciseResponse -> exerciseResponse.day }
    val executions: List<String> = exercises
            .map { e -> e.series + " x " + e.quantity + " " + e.unit}

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.exercise_list_item, null, true)

        val nameText = rowView.findViewById(R.id.username) as TextView
        val dayText = rowView.findViewById(R.id.day) as TextView
        val executionText = rowView.findViewById(R.id.execution) as TextView

        nameText.text = names[position]
        dayText.text = "Day " + days[position]
        executionText.text = executions[position]

        return rowView
    }
}