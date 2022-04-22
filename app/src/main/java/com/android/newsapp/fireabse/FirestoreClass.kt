package com.android.newsapp.fireabse

import com.google.firebase.auth.FirebaseAuth

class FirestoreClass {


    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }


}
