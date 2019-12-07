package pl.pollub.trenzoneapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.google.gson.internal.LinkedTreeMap
import pl.pollub.trenzoneapp.api.ActiveTrainingsApiData
import pl.pollub.trenzoneapp.api.ActiveTrainingsResponse
import pl.pollub.trenzoneapp.constraint.Mark

class ActiveTrainingListActivity : AppCompatActivity() {

    internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.active_training_list_activity)

        val bundle: Bundle = intent.extras
        val username = bundle.get("username") as String
        val token = bundle.get("token") as String
        val mark = bundle.get("mark") as String

        ActiveTrainingsApiData.apiData(mark, username, object : ActiveTrainingsApiData.Response {
            override fun data(content: ActiveTrainingsResponse, status: Boolean) {
                if (status) {

                    val trainings: LinkedTreeMap<String, List<String>> = content.map as LinkedTreeMap
                    expandableListView = findViewById(R.id.activeTrainingList)
                    if (expandableListView != null) {
                        val listData: LinkedTreeMap<String, List<String>> = trainings
                        val idList: List<String> = content.ids
                        val activeIdList: List<String> = content.activeIds
                        titleList = ArrayList(listData.keys)
                        adapter = ActiveTrainingsListAdapter(this@ActiveTrainingListActivity, titleList as ArrayList<String>, listData)
                        expandableListView!!.setAdapter(adapter)

/*                      expandableListView!!.setOnGroupExpandListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<String>)[groupPosition] + " List Expanded.", Toast.LENGTH_SHORT).show() }

                        expandableListView!!.setOnGroupCollapseListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.", Toast.LENGTH_SHORT).show() }*/


                        if(mark == Mark.DAY.toString().toLowerCase()) {
                            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                                //Toast.makeText(applicationContext, "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition), Toast.LENGTH_SHORT).show()
                                intent = Intent(this@ActiveTrainingListActivity, ExerciseUseListActivity::class.java)
                                intent.putExtra("day", listData[(titleList as ArrayList<String>)[groupPosition]]!![childPosition])
                                intent.putExtra("trainingId", idList[groupPosition])
                                intent.putExtra("activeTrainingId", activeIdList[groupPosition])
                                intent.putExtra("trainingName", titleList!![groupPosition])
                                intent.putExtra("username", username)
                                intent.putExtra("token", token)
                                startActivity(intent)
                                false
                            }
                        }

                        if(mark == Mark.ACHIEVE.toString().toLowerCase()) {
                            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                                //Toast.makeText(applicationContext, "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition), Toast.LENGTH_SHORT).show()
                                intent = Intent(this@ActiveTrainingListActivity, AchievementListActivity::class.java)
                                intent.putExtra("date", listData[(titleList as ArrayList<String>)[groupPosition]]!![childPosition])
                                intent.putExtra("trainingId", idList[groupPosition])
                                intent.putExtra("activeTrainingId", activeIdList[groupPosition])
                                intent.putExtra("trainingName", titleList!![groupPosition])
                                intent.putExtra("username", username)
                                startActivity(intent)
                                false
                            }
                        }
                    }
                }
            }
        })

    }
}
