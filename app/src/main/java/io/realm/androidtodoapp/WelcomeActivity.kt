package io.realm.androidtodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.mongodb.Credentials

class WelcomeActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private var password = "123456"
    private lateinit var loginButton: Button

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        username = findViewById(R.id.nickname)
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener { login() }
    }

    private fun login() {
        loginButton.isEnabled = false

        val username = this.username.text.toString()
        val creds = Credentials.emailPassword(username, password)
        Log.v("login as", creds.asJson())
        toDoApp.loginAsync(creds) {
            // re-enable the buttons after
            loginButton.isEnabled = true
            if (!it.isSuccess) {
                onLoginFailed(it.error.message ?: "An error occurred.")
            } else {
                onLoginSuccess()
            }
        }
    }

    private fun onLoginSuccess() {
        // successful login ends this activity, bringing the user back to the task activity
        Log.v("Login Activity", "Login Success")
        finish()
    }

    private fun onLoginFailed(errorMsg: String) {
        Log.e("Login Activity", errorMsg)
        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_LONG).show()
    }
}