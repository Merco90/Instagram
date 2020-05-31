package org.rasuloff.instagram

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, TextWatcher,
    View.OnClickListener {
    private val TAG = "LoginActivity"
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "onCreate")

        KeyboardVisibilityEvent.setEventListener(this, this)
        login_button.isEnabled = false
        mail_input.addTextChangedListener(this)
        password_input.addTextChangedListener(this)
        login_button.setOnClickListener(this)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View) {
        val mail = mail_input.text.toString()
        val password = password_input.text.toString()
        if (validate(mail, password)) {
            firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        } else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if (isKeyboardOpen) {
            scrool_view.scrollTo(0, scrool_view.bottom)
            create_account_text.visibility = View.GONE
        } else {
            scrool_view.scrollTo(0, scrool_view.top)
            create_account_text.visibility = View.VISIBLE
        }
    }

    override fun afterTextChanged(s: Editable?) {
        login_button.isEnabled =
            validate(mail_input.text.toString(), password_input.text.toString())
//                    mail_input.text.toString().isNotEmpty() &&
//                    password_input.text.toString().isNotEmpty()
        //if (email_input.text.toString().isNotEmpty() && password_input.text.toString().isNotEmpty())
    }

    private fun validate(mail: String, password: String) =
        mail.isNotEmpty() && password.isNotEmpty()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

}
