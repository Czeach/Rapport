package com.czech.rapport.data.repositories.auth.employee

import com.czech.rapport.data.models.EmployeeInfo
import com.czech.rapport.utils.Constants.COMPANIES
import com.czech.rapport.utils.Constants.EMPLOYEES
import com.czech.rapport.utils.states.DataState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EmployeeAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : EmployeeAuthRepository{

    override suspend fun createEmployee(employee: EmployeeInfo): Flow<DataState<String>> {
        return flow<DataState<String>> {
            emit(DataState.loading())

            firebaseAuth.createUserWithEmailAndPassword(
                employee.workEmail.toString(),
                employee.password.toString()
            ).addOnCompleteListener { task ->
                try {
                    when (task.isSuccessful) {
                        true -> {
                            flow {
                                emit(DataState.data(data = "Company authentication successful"))

                                val employeeInfo = EmployeeInfo(
                                    id = firebaseAuth.currentUser?.uid,
                                    firstName = employee.firstName,
                                    lastName = employee.lastName,
                                    workEmail = employee.workEmail,
                                    currentCompany = employee.currentCompany,
                                    positionAtCompany = employee.positionAtCompany,
                                    password = employee.password
                                )

                                firebaseFirestore.collection(COMPANIES)
                                    .document(employee.currentCompany!!)
                                    .collection(EMPLOYEES)
                                    .document("${employee.firstName} ${employee.lastName}")
                                    .set(employeeInfo)
                                    .addOnSuccessListener {
                                        flow {
                                            emit(DataState.data(data = "Successfully created employee account"))
                                        }.flowOn(Dispatchers.IO)
                                    }
                                    .addOnFailureListener {
                                        flow<DataState<String>> {
                                            emit(DataState.error(message = "Employee account creation failed"))
                                        }.flowOn(Dispatchers.IO)
                                    }
                            }.flowOn(Dispatchers.IO)
                        }
                        false -> {
                            flow<DataState<String>> {
                                emit(DataState.error(message = "Employee authentication failed"))
                            }.flowOn(Dispatchers.IO)
                        }
                    }
                } catch (e: FirebaseException) {
                    flow<DataState<String>> {
                        emit(DataState.error(message = e.message.toString()))
                    }.flowOn(Dispatchers.IO)
                }
            }

        }.flowOn(Dispatchers.IO)
    }
}