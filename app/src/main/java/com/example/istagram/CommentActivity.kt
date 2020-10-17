package com.example.istagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.istagram.adapter.CommentsAdapter
import com.example.istagram.model.Comment
import com.example.istagram.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.activity_login.*

class CommentActivity : AppCompatActivity() {

    private var postId = ""
    private var publisherId =""
    private var firebaseUser: FirebaseUser? = null
    private var commentAdapter: CommentsAdapter? = null
    private var comentList: MutableList<Comment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val intent = intent
        postId = intent.getStringExtra("postId")
        publisherId = intent.getStringExtra("publisherId")

        firebaseUser = FirebaseAuth.getInstance().currentUser

        var recyclerView: RecyclerView? = null
        recyclerView = findViewById(R.id.recyclerView_comments)
            val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        recyclerView.layoutManager = linearLayoutManager

        comentList = ArrayList()
        commentAdapter = CommentsAdapter(this,comentList as ArrayList<Comment>)
        recyclerView.adapter = commentAdapter

        userInfo()
        txtpost_comments.setOnClickListener {
            if (add_comment!!.text.toString() == "") {

                Toast.makeText(
                    this@CommentActivity,
                    "Please write Comment First",
                    Toast.LENGTH_SHORT
                )

            } else {
                addComment()
            }
        }
    }
    private fun addComment() {
        val commentsReference = FirebaseDatabase.getInstance().reference
            .child("Comments").child(postId!!)

        val commentsMap = HashMap<String,Any>()
        commentsMap["comment"] = add_comment!!.text.toString()
        commentsMap["publisher"] = firebaseUser!!.uid

        commentsReference.push().setValue(commentsMap)

        add_comment!!.text.clear()
    }
    private fun userInfo(){
        val usersReference = FirebaseDatabase.getInstance().reference
            .child("Users").child(firebaseUser!!.uid)

        usersReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user = p0.getValue(User::class.java)
                    
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                
            }

        })

    }
}