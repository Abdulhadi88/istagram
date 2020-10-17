package com.example.istagram.model

import android.media.Image

class User {
    private var username: String = ""
    private var fullname: String = ""
    private var bio: String = ""
    private var Image: String = ""
    private var uid: String = ""

    constructor()

    constructor(username: String, fullname: String, bio: String, image: String, uid: String) {
        this.username = username
        this.fullname = fullname
        this.bio = bio
        this.Image = image
        this.uid = uid
    }

    fun getUsername(): String {
        return username
    }

    fun setUsername(username: String ) {
        this.username = username
    }

    fun getFullname(): String {
        return fullname
    }

    fun setFullname(fullname: String) {
        this.fullname = fullname
    }

    fun getBio(): String {
        return bio
    }

    fun setBio(bio: String) {
        this.bio = bio
    }

    fun getImage(): String{
        return Image
    }

    fun setImage(image: String) {
        this.Image = image
    }

    fun getUID(): String{
        return uid
    }

    fun setUID(uid :String){
        this.uid = uid
    }
}