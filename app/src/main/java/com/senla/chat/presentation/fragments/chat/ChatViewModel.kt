package com.senla.chat.presentation.fragments.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.senla.chat.models.SearchTerms
import com.senla.chat.models.User
import com.senla.chat.presentation.fragments.utils.PreferenceManager
import javax.inject.Inject

class ChatViewModel @Inject constructor(private val database: FirebaseFirestore) : ViewModel() {
    val receivedUser = MutableLiveData<User>()

    private var idUserInDb: String = ""

    fun sendYourUserData(searchTerms: SearchTerms, preferenceManager: PreferenceManager) {
        val userMap: HashMap<String, Any> = hashMapOf()
        val userId = (0..Int.MAX_VALUE).random().toString()
        userMap.put(KEY_YOUR_ID, userId)
        userMap.put(KEY_YOUR_GENDER, searchTerms.yourGender)
        userMap.put(KEY_YOUR_AGE, searchTerms.yourAge.toString())
        database.collection(KEY_COLLECTION_USERS)
            .add(userMap)
            .addOnSuccessListener { documentReference ->
                preferenceManager.putString(ChatFragment.KEY_USER_ID, userId.toString())
                idUserInDb = documentReference.id
                preferenceManager.putString(KEY_USER_ID_IN_DB, idUserInDb)
                Log.d("VIEWMODEL", "idUserInDb: " + idUserInDb.toString())
            }
            .addOnFailureListener { exception ->
                Log.d("VIEWMODEL", exception.toString())
            }

    }

    fun loadReceiverUser(searchTerms: SearchTerms) {
        val users: ArrayList<User> = arrayListOf()
        database.collection(KEY_COLLECTION_USERS)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val queryDocumentSnapShot = task.result
                    queryDocumentSnapShot?.forEach {
                        val user = User(
                            id = it.getString(KEY_YOUR_ID).toString(),
                            age = it.getString(KEY_YOUR_AGE)!!.toInt(),
                            gender = it.getString(KEY_YOUR_GENDER)!!,
                        )
                        users.add(user)
                        Log.d("VIEWMODEL", user.toString() + "DSHFAHDFKHKDHSHf")
                    }
                }
                receivedUser.value = sort(users, searchTerms).random()
            }

    }

    private fun sort(userArray: ArrayList<User>, searchTerms: SearchTerms): ArrayList<User> {
        val sortUserArray = arrayListOf<User>()
        userArray.forEach {
            if (it.age == searchTerms.otherPersonAge && it.gender == searchTerms.otherPersonGender)
                sortUserArray.add(it)
        }
        return sortUserArray
    }

    fun deleteUser(){
        database.collection(KEY_COLLECTION_USERS)
            .document(idUserInDb)
            .delete()
            .addOnSuccessListener { Log.d("VIEWMODEL", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener {  e -> Log.d("VIEWMODEL", "Error deleting document", e) }
    }

    companion object {
        const val KEY_COLLECTION_USERS = "user"
        const val KEY_YOUR_GENDER = "your_gender"
        const val KEY_YOUR_AGE = "your_age"
        const val KEY_YOUR_ID = "your_id"
        const val KEY_USER_ID_IN_DB = "user_id_in_db"
        const val KEY_OTHER_AGE = "other_age"
    }
}