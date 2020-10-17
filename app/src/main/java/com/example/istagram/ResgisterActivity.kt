package com.example.istagram

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_resgister.*

class ResgisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resgister)

        btn_signin_link.setOnClickListener {
            startActivity(Intent(this@ResgisterActivity,LoginActivity::class.java))
        }

        btn_register.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        //untuk memberikan aksi ketika text dimasukan kedalam edit text,dan masuk dalam string
        val fullName = fullname_register.text.toString()
        val userName = username_register.text.toString()
        val email = email_register.text.toString()
        val password = password_register.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this,"fullname Is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this,"username Is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"email Is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"password Is required", Toast.LENGTH_LONG).show()

            else->{
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Register")
                progressDialog.setMessage("Please Wait...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            saveUserInfo(fullName,userName,email,progressDialog)
                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this,"Error: $message", Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()

                        }
                    }

            }
        }

    }

    private fun saveUserInfo(
        fullName: String,
        userName: String,
        email: String,
        progressDialog: ProgressDialog
    ) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullName.toLowerCase()
        userMap["username"] = userName.toLowerCase()
        userMap["emal"] = email
        userMap["bio"] = "hey iam student at idn"
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/sosial-media-8f2ec.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=eee53f51-03bb-441f-b3c7-dde7333f8638"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    Toast.makeText(this@ResgisterActivity,"Account sudah dibuat", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ResgisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                }else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this,"Error: $message", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }


    }
}