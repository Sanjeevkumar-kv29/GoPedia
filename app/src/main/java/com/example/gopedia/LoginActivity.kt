package com.example.gopedia

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private var firebaseAuth: FirebaseAuth? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)


        val user = firebaseAuth?.getCurrentUser()

        if (user != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))

        }


        loginbttn?.setOnClickListener(View.OnClickListener {

            val regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$".toRegex()

            if (loginid.text.toString().isEmpty()){
                loginid.setError("Enter Login ID")
            }
            else if (loginpass.text.toString().isEmpty()){
                loginpass.setError("Enter The Password")
            }
            else if (loginid.text.toString().matches(regex)){
                validate(
                    loginid?.getText().toString(),
                    loginpass?.getText().toString()
                )


            }
            else{
                loginid.setError("enter valid email")
            }



        })


        /*

        val sharepref:SharedPreferences = getSharedPreferences("uidpass",0)

        loginid.setText(sharepref.getString("user",""))
        loginpass.setText(sharepref.getString("pass",""))



        loginbttn.setOnClickListener {
             if (loginid.text.toString()==muser && loginpass.text.toString()==mpass)
             {
                 sharepref.edit().putString("user",loginid.text.toString()).apply()
                 sharepref.edit().putString("pass",loginpass.text.toString()).apply()
                 Toast.makeText(this,"Login SuccessFull",Toast.LENGTH_LONG).show()
             }

             else{
                 Toast.makeText(this,"Invalid Credential",Toast.LENGTH_LONG).show()
             }

         }*/


        log2reg.setOnClickListener {

            startActivity(Intent(this,RegisterActivity::class.java))
            overridePendingTransition(R.anim.leftout,R.anim.rightin)

        }

        forgetpass.setOnClickListener {

            startActivity(Intent(this,Forgetpassword::class.java))
            overridePendingTransition(R.anim.leftout,R.anim.rightin)

        }

    }


    override fun onBackPressed() {

        Toast.makeText(this,"Back press Restricted", Toast.LENGTH_LONG).show()
    }


    private fun validate(userName: String, userPassword: String) {

        progressDialog?.setMessage("Hey! By using this app we can help you to learn better.")
        progressDialog?.show()
        progressDialog?.setCanceledOnTouchOutside(false)

        firebaseAuth?.signInWithEmailAndPassword(userName, userPassword)
            ?.addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    progressDialog?.dismiss()
                    //Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification()

                } else {
                    Toast.makeText(this, "Login Failed! or Your Are not Registered Or incorrect Password", Toast.LENGTH_SHORT).show()
                    // counter--
                    // Info.setText("Number of attempts remaining:$counter")
                    progressDialog?.dismiss()
                    //if (counter == 0) {
                    //login.setEnabled(false)
                }
            })
    }


    private fun checkEmailVerification() {
        val firebaseUser = firebaseAuth?.getCurrentUser()
        val emailflag = firebaseUser!!.isEmailVerified()

        if (emailflag) {
            finish()


            SaveToSharedpref(firebaseAuth?.getCurrentUser()?.email.toString())

            startActivity(Intent(this, MainActivity::class.java))

        } else {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show()
            firebaseAuth?.signOut()
        }
    }

    private fun SaveToSharedpref(Uemail: String) {
        val sharepref:SharedPreferences = getSharedPreferences("UserDetails",0)
        sharepref.edit().putString("useremail",Uemail).apply()

        Toast.makeText(this, "Verify your email"+Uemail, Toast.LENGTH_SHORT).show()

    }


}