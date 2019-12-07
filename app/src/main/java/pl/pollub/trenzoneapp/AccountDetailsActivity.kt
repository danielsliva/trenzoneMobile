package pl.pollub.trenzoneapp

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.account_details_activity.*
import pl.pollub.trenzoneapp.api.AccountApiData
import pl.pollub.trenzoneapp.api.AccountDetails

class AccountDetailsActivity : AppCompatActivity() {

    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_details_activity)

        progressBar.visibility = View.VISIBLE

        val bundle: Bundle = intent.extras
        val username = bundle.get("username") as String

        getAccountDetails(username)

    }

    private fun getAccountDetails(username: String) {
        AccountApiData.apiData(username ,object : AccountApiData.Response {
            override fun data(content: AccountDetails, status: Boolean) {
                if (status) {
                    name_lastname.text = content.name + " " + content.lastName
                    username2.text = content.username
                    email2.text = content.email
                    age2.text = content.age
                    height2.text = content.height
                    weight2.text = content.weight

                    if (content.trainerDetails != null) {
                        city.visibility = View.VISIBLE
                        job.visibility = View.VISIBLE
                        phone.visibility = View.VISIBLE
                        description.visibility = View.VISIBLE
                        trainer.visibility = View.VISIBLE
                        name_lastname.visibility = View.VISIBLE
                        username1.visibility = View.VISIBLE
                        email.visibility = View.VISIBLE
                        age.visibility = View.VISIBLE
                        height.visibility = View.VISIBLE
                        weight.visibility = View.VISIBLE
                        city2.text = content.trainerDetails.city
                        job2.text = content.trainerDetails.job
                        phone2.text = content.trainerDetails.phoneNumber
                        description2.text = content.trainerDetails.description
                    }
                    progressBar.visibility = View.GONE
                }
            }
        })
    }
}
