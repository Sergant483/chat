package com.senla.chat.presentation.fragments.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.senla.chat.models.ChatState
import com.senla.chat.models.SearchTerms
import com.senla.chat.models.User
import com.senla.chat.presentation.fragments.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(private val database: FirebaseFirestore) : ViewModel() {
    private val _receivedUser = MutableLiveData<User>()
    val receivedUser: LiveData<User> = _receivedUser

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId

    private val _chatState = MutableLiveData<ChatState>(ChatState.NONE)
    val chatState: LiveData<ChatState> = _chatState

    fun doChatStateLoading() {
        viewModelScope.launch {
            _chatState.value = ChatState.LOADING
            delay(20_000L)
            doChatStateError()
        }
    }

    fun doChatStateError() {
        _chatState.value = ChatState.ERROR
    }

    fun doChatStateDialog() {
        _chatState.value = ChatState.DIALOG
    }

    private var idUserInDb: String = ""
    private var yourId: String = ""

    fun sendYourUserData(searchTerms: SearchTerms, preferenceManager: PreferenceManager) {
        val userMap: HashMap<String, Any> = hashMapOf()
        val userId = (0..Int.MAX_VALUE).random().toString()
        yourId = userId
        userMap.put(KEY_YOUR_ID, userId)
        userMap.put(KEY_YOUR_GENDER, searchTerms.yourGender)
        userMap.put(KEY_YOUR_AGE, searchTerms.yourAge.toString())
        userMap.put(KEY_YOUR_ONLINE, 1)
        database.collection(KEY_COLLECTION_USERS)
            .add(userMap)
            .addOnSuccessListener { documentReference ->
                preferenceManager.putString(ChatFragment.KEY_USER_ID, userId)
                idUserInDb = documentReference.id
                preferenceManager.putString(KEY_USER_ID_IN_DB, idUserInDb)
                _userId.value = idUserInDb
                Log.d("VIEWMODEL", "idUserInDb: " + idUserInDb)
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
                            inSearch = it.getLong(KEY_YOUR_ONLINE)!!
                        )
                        users.add(user)
                        Log.d("VIEWMODEL", user.toString() + "DSHFAHDFKHKDHSHf")
                    }
                }
                val sortedUsers = sort(users, searchTerms)
                if (!sortedUsers.isNullOrEmpty()) {
                    _receivedUser.value = sortedUsers.first()
                }
            }
    }

    private fun sort(userArray: ArrayList<User>, searchTerms: SearchTerms): ArrayList<User> {
        val sortUserArray = arrayListOf<User>()
        userArray.forEach {
            if (it.age == searchTerms.otherPersonAge && it.gender == searchTerms.otherPersonGender && it.inSearch == 1L && it.id != yourId) {
                sortUserArray.add(it)
                Log.d("TAGG", "sortUserArray.add(it)")
            }
        }
        return sortUserArray
    }

    fun doSearchStateFalse() {
        val documentReference: DocumentReference =
            database.collection(KEY_COLLECTION_USERS)
                .document(yourId)
        documentReference.update(
            KEY_YOUR_ONLINE, 0
        )
    }


    companion object {
        const val KEY_COLLECTION_USERS = "user"
        const val KEY_YOUR_GENDER = "your_gender"
        const val KEY_YOUR_AGE = "your_age"
        const val KEY_YOUR_ID = "your_id"
        const val KEY_USER_ID_IN_DB = "user_id_in_db"
        const val KEY_YOUR_ONLINE = "your_online"
    }
}