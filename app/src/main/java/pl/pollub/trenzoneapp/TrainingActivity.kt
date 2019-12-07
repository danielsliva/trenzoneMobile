package pl.pollub.trenzoneapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.training_activity.*

class TrainingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.training_activity)

        val nameText = findViewById<TextView>(R.id.name)
        val usernameText = findViewById<TextView>(R.id.username)
        val descText = findViewById<TextView>(R.id.desc)
        val dateText = findViewById<TextView>(R.id.date)
        val rateText = findViewById<TextView>(R.id.rate)
        val diffText = findViewById<TextView>(R.id.diff)

        val bundle: Bundle = intent.extras

        nameText.text = bundle.getString("name")
        usernameText.text = bundle.getString("username")
        descText.text = bundle.getString("desc")
        dateText.text = bundle.getString("date")
        rateText.text = bundle.getString("rate")
        diffText.text = bundle.getString("diff")
        val id: String = bundle.getString("id")

        button.setOnClickListener() {
            intent = Intent(this, ExerciseListActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

    }
}
