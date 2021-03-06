package com.example.gopedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_email_auth.*

class EmailAuth : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null
    var fsdb = FirebaseFirestore.getInstance()
    var email =""
    var pass=""
    var mobile=""
    var name=""
    var user_email=""
    var user_password=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_auth)


        firebaseAuth = FirebaseAuth.getInstance()


        val intent=intent
        email= intent.getStringExtra("email")!!.trim()
        pass = intent.getStringExtra("pass")!!
        mobile = intent.getStringExtra("mobile")!!
        name= intent.getStringExtra("name")!!

        user_email = email.toString().trim { it <= ' ' }
        user_password = pass.toString().trim { it <= ' ' }

        emailauth()


        button2log.setOnClickListener {
            finish()
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(R.anim.leftin,R.anim.rightout)
        }


    }

    override fun onBackPressed() {


    }

    fun emailauth()
    {
        firebaseAuth?.createUserWithEmailAndPassword(user_email, user_password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEmailVerification()
                } else {
                    Toast.makeText(
                        this,
                        "Registration Failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }





    private fun sendEmailVerification() {

        val firebaseUser = firebaseAuth?.getCurrentUser()
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {


                //fbfs(name,email,mobile)
                Toast.makeText(this,"Verification mail sent! Successfully", Toast.LENGTH_LONG).show()
                firebaseAuth?.signOut()
                progressbaremail.visibility = View.GONE
                emailsentmsg.visibility = View.VISIBLE
                button2log.visibility = View.VISIBLE


            } else {
                Toast.makeText(
                    this,
                    "Verification mail has'nt been sent!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }





}