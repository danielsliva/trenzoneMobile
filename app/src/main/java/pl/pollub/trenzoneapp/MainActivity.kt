package pl.pollub.trenzoneapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.beardedhen.androidbootstrap.TypefaceProvider
import kotlinx.android.synthetic.main.activity_main.*
import pl.pollub.trenzoneapp.api.LoginApiData
import pl.pollub.trenzoneapp.api.LoginResponse


class MainActivity : AppCompatActivity() {

    lateinit var toast: Toast
    lateinit var v: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TypefaceProvider.registerDefaultIconSets()

        val usernameText = findViewById<TextView>(R.id.username)
        val passwordText = findViewById<TextView>(R.id.password)

        login.setOnClickListener {
            if (usernameText.text.isNullOrEmpty() || passwordText.text.isNullOrEmpty()) {
                toast = Toast.makeText(this, "Nazwa użytkownika i hasło muszą być podane", Toast.LENGTH_SHORT)
                v = toast.view.findViewById(android.R.id.message) as TextView
                v.setTextColor(Color.RED)
                toast.show()
            } else {

                LoginApiData.apiData(usernameText.text.toString(), passwordText.text.toString(), object : LoginApiData.Response {
                    override fun data(content: LoginResponse?, status: Boolean) {
                        if (status) {
                            val loginResponse: LoginResponse = content!!
                            val intent = Intent(this@MainActivity, UserBoardActivity::class.java)
                            intent.putExtra("username", loginResponse.username)
                            intent.putExtra("token", loginResponse.token)
                            startActivity(intent)

                        } else {
                            toast = Toast.makeText(this@MainActivity, "Niewłaściwa nazwa użytkownika lub hasło", Toast.LENGTH_SHORT)
                            v = toast.view.findViewById(android.R.id.message) as TextView
                            v.setTextColor(Color.RED)
                            toast.show()
                        }
                    }
                })
            }
        }

    }
}
