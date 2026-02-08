package com.example.woordup.data.repository

import com.example.woordup.data.model.Word
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

import com.google.firebase.auth.FirebaseAuth

class WordRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : WordRepository {

    private val collection
        get() = firestore.collection("users")
            .document(auth.currentUser?.uid ?: "anonymous")
            .collection("words")

    override suspend fun addWord(word: Word) {
        if (auth.currentUser == null) return
        val documentRef = collection.document()
        val wordWithId = word.copy(id = documentRef.id)
        documentRef.set(wordWithId).await()
    }

    override fun getWords(): Flow<List<Word>> = callbackFlow {
        if (auth.currentUser == null) {
            close()
            return@callbackFlow
        }
        val listenerRegistration = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                trySend(snapshot.toObjects<Word>())
            }
        }
        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun updateWord(word: Word) {
        if (auth.currentUser == null) return
        collection.document(word.id).set(word).await()
    }

    override suspend fun deleteWord(wordId: String) {
        if (auth.currentUser == null) return
        collection.document(wordId).delete().await()
    }
}
