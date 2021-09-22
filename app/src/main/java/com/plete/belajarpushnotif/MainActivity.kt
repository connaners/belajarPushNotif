package com.plete.belajarpushnotif

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnProfile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful){
                Log.w(TAG, "fetching fcm registration token failed", task.exception)
                return@OnCompleteListener
            }

            //get new fcm regist token
            val token = task.result

            //log and toast
            val msg = getString(R.string.tkn_fmc, token)
            Log.d(TAG, "token "+msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        FirebaseMessaging.getInstance().subscribeToTopic("all")
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    //success subsribed to notif service
                    Log.d(TAG, "success send to all")
                } else {
                    //subs notif service failed
                    Log.d(TAG, "fail send to all")
                }
            }
    }
}