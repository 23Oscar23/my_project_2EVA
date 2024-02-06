package es.otm.myproject

import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import es.otm.myproject.classes.Chat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseChat {

    private val firestore by lazy { FirebaseFirestore.getInstance()}
    private val auth = AuthManager()
    private val userId = auth.getCurrentUser()?.email

    suspend fun add(chat: Chat): Boolean{
        return try {
            chat.userId = userId.toString()
            chat.createdAt = Timestamp.now().toDate()
            firestore.collection("Chat").add(chat).await()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun getNotesFlow(): Flow<List<Chat>> = callbackFlow{
        var chatCollection: CollectionReference? = null
        try {
            chatCollection = FirebaseFirestore.getInstance().collection("Chat")
            val subscription = chatCollection?.orderBy("createdAt", Query.Direction.ASCENDING)?.addSnapshotListener{ snapshot, _ ->
                if (snapshot != null) {
                    val chats = mutableListOf<Chat>()
                    snapshot?.forEach {
                        chats.add(
                            Chat(
                                id = it.id,
                                title = it.get("title").toString(),
                                content = it.get("content").toString(),
                            )
                        )
                    }
                    trySend(chats)
                }
            }
            awaitClose { subscription?.remove() }
        }catch (e : Throwable){
            close(e)
        }
    }


}