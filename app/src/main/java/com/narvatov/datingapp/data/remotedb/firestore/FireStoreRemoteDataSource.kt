package com.narvatov.datingapp.data.remotedb.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.narvatov.datingapp.data.remotedb.RemoteDataSource

abstract class FireStoreRemoteDataSource : RemoteDataSource() {

    val db = Firebase.firestore

    abstract val collectionName: String

    protected val collection: CollectionReference
        get() = db.collection(collectionName)

}