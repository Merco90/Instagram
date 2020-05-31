package org.rasuloff.instagram

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(0) {
    private val TAG = "MainActivity"
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG, "onCreate")
        setupBottomNavigation()

        auth = FirebaseAuth.getInstance()
//        auth.signOut()
//        auth.signInWithEmailAndPassword("arasulzade@gmail.com", "Rasulov90")
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    Log.d(TAG, "signIn: Success")
//                } else {
//                    Log.e(TAG, "signIn: Error", it.exception)
//                }
//            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            startActivity(Intent (this, LoginActivity::class.java))
            finish()
        }
    }
}

