package pt.isel.unicommunityprototype.repository.data_access

import android.util.Log
import com.google.firebase.firestore.*
import pt.isel.unicommunityprototype.UniCommunityApplication
import pt.isel.unicommunityprototype.common.NotificationGenerator
import pt.isel.unicommunityprototype.repository.data_access.dto.AnnouncementDto

class FirestoreIntermediator(
    private val app: UniCommunityApplication,
    private val notificationGen: NotificationGenerator //TODO: check if this depedency is correct in terms of architecture
) {
    val TAG = "FirestoreIntermediator"

    val db = FirebaseFirestore.getInstance()
    private val boardsCol = db.collection("boards")

    //codigo de escrever um documento na col (create announcement numa determinada board)
    fun createAnnouncement(boardId: Int, title: String, content: String) {
        // Create a new announcement
        val announcement = HashMap<String, Any>()
        announcement["title"] = title
        announcement["content"] = content
        announcement["created"] = FieldValue.serverTimestamp()//TODO: is this how we get the date of the server?

        // Add a new document(announcement) on the specified board
        boardsCol
            .document(boardId.toString())
            .collection("announcements")
            .add(announcement)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Announcement created with success with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding announcement document", e)
            }
    }

    fun registerListenerForAnnouncement(boardId: Int) {
        boardsCol
            .document(boardId.toString())
            .collection("announcements")
            .addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "Error: Listen failed.", e)
                    return@EventListener
                }

                //runAsync {
                for (dc in snapshots!!.documentChanges)
                    if (dc.type == DocumentChange.Type.ADDED /*&& dc.document.id!= dummy*/) {
                        val announcementDto = dc.document.toObject(AnnouncementDto::class.java)
                        val boardName = app.repository.getBoardById(boardId)?.name

                        notificationGen.showNotification(
                            "$boardName: ${announcementDto.title}",
                            announcementDto.content)
                    }
            })
    }
    //codigo de subcrever listener numa certa col (estar a ouvir por notificaçoes de uma determinada board)
}