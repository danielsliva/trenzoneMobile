package pl.pollub.trenzoneapp

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import pl.pollub.trenzoneapp.api.ExerciseResponse
import java.util.*


class ExercisesUseListAdapter(private val context: Context, exercises2: List<ExerciseResponse>) : BaseAdapter() {

    init {
        exercises = exercises2
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val holder: ViewHolder

        val names: List<String> = exercises.map { exerciseResponse -> exerciseResponse.name }
        val series: List<String> = exercises.map { exerciseResponse -> exerciseResponse.series }
        val quantities: List<String> = exercises.map { exerciseResponse -> exerciseResponse.quantity }
        val values: List<Float> = exercises.map { exerciseResponse -> exerciseResponse.quantity.toFloat() * exerciseResponse.series.toFloat() }

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.active_exercise_list_item, null, true)
            holder.editText = convertView!!.findViewById(R.id.exerciseProgress)
            holder.checkBox = convertView.findViewById(R.id.exerciseDone)

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val nameText = convertView.findViewById(R.id.exerciseName) as TextView
        val seriesText = convertView.findViewById(R.id.exerciseSeries) as TextView
        val quantityText = convertView.findViewById(R.id.exerciseQuentity) as TextView
        val valueText = convertView.findViewById(R.id.exerciseValue) as TextView

        holder.editText!!.setText(exercises[position].editText)
        holder.checkBox!!.isChecked = exercises[position].checkBox
        nameText.text = names[position]
        seriesText.text = series[position]
        quantityText.text = quantities[position]
        valueText.text = values[position].toString()

        holder.editText!!.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                exercises[position].editText = holder.editText!!.text.toString()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        holder.checkBox!!.setOnClickListener {
            exercises[position].checkBox = holder.checkBox!!.isChecked
            if (holder.checkBox!!.isChecked) {
                holder.editText!!.visibility = View.VISIBLE
            }
        }
        return convertView
    }

    override fun getItem(position: Int): Any {
        return exercises[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return exercises.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return count
    }

    private inner class ViewHolder {
        var editText: EditText? = null
        var checkBox: CheckBox? = null
    }

    companion object {
        var exercises: List<ExerciseResponse> = Collections.emptyList()
    }

}