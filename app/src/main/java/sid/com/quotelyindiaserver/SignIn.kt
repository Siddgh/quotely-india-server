package sid.com.quotelyindiaserver

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val uid = auth.currentUser!!.uid
            goToHome(uid)
        } else {

            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )


            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(true)
                    .build(),
                Constants.RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(
                    this,
                    "Welcome to Quotely India, ${user?.displayName}",
                    Toast.LENGTH_SHORT
                ).show()

                goToHome(user!!.uid)
            } else {
                Log.d("Error:", response!!.error.toString())
            }
        }
    }

    fun goToHome(uid: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.USER_ID, uid)
        startActivity(intent)
        finish()
    }
}
