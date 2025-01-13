package com.yesitlab.compro



import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.network.SessionManager
import com.yesitlab.compro.activity.authActivity.AuthActivity
import com.yesitlabs.lawco.AuthEventManager


object AuthObserverHelper {


    fun observeAuthEvents(activity: AppCompatActivity, authRequiredLiveData: LiveData<Boolean>) {
        authRequiredLiveData.observe(activity) { isAuthRequired ->
            if (isAuthRequired == true) {
                Log.d("TESTING_Auth", "Response Error 23")
                activity.handleAuthEvent()
            }
        }
    }

    private fun AppCompatActivity.handleAuthEvent() {
        val commonUtils = SessionManager(this)
     //   commonUtils.logOutAccount(this)
        AuthEventManager.notifyAuthFalse()
        Toast.makeText(this,"Session Expired", Toast.LENGTH_LONG).show()
        val intent = Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }


}