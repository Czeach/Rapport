package com.czech.rapport.data.repositories.auth.login

import com.czech.rapport.utils.states.DataState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): LoginRepository {
    override fun login(email: String, password: String): Flow<DataState<String>> {
        return flow<DataState<String>> {
            emit(DataState.loading())

            try {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = firebaseAuth.currentUser

                            user.let {
                                val name = user?.displayName
                            }
                        }
                    }.await()

            }catch (e: Throwable) {
                emit(DataState.error(message = e.message.toString()))
            } catch (e: FirebaseException) {
                emit(DataState.error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}