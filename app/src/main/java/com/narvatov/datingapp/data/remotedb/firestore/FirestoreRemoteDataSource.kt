package com.narvatov.datingapp.data.remotedb.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.narvatov.datingapp.data.remotedb.firestore.delegate.coroutine.CoroutineDelegate
import com.narvatov.datingapp.data.remotedb.firestore.delegate.coroutine.ICoroutineDelegate
import com.narvatov.datingapp.data.remotedb.firestore.delegate.dispatcher.DispatcherDelegate
import com.narvatov.datingapp.data.remotedb.firestore.delegate.dispatcher.IDispatcherDelegate
import com.narvatov.datingapp.delegate.common.context.ContextDelegate
import com.narvatov.datingapp.delegate.common.context.IContextDelegate

abstract class FirestoreRemoteDataSource : IDispatcherDelegate by DispatcherDelegate, IContextDelegate by ContextDelegate,
    ICoroutineDelegate by CoroutineDelegate {

    private val db = Firebase.firestore

    abstract val collectionName: String

    protected val collection: CollectionReference
        get() = db.collection(collectionName)

}