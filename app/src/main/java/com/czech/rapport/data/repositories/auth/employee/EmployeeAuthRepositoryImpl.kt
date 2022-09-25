package com.czech.rapport.data.repositories.auth.employee

import com.czech.rapport.data.models.EmployeeInfo
import com.czech.rapport.utils.Constants.COMPANIES
import com.czech.rapport.utils.Constants.EMPLOYEES
import com.czech.rapport.utils.states.DataState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EmployeeAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : EmployeeAuthRepository{

    private val employeeExists = MutableStateFlow<Boolean?>(false)

    override suspend fun createEmployee(employee: EmployeeInfo): Flow<DataState<String>> {
        return flow<DataState<String>> {
            emit(DataState.loading())

            val employeeInfo = EmployeeInfo(
                id = firebaseAuth.currentUser?.uid,
                firstName = employee.firstName,
                lastName = employee.lastName,
                workEmail = employee.workEmail,
                currentCompany = employee.currentCompany,
                positionAtCompany = employee.positionAtCompany,
                password = employee.password
            )

            try {
                firebaseFirestore.collection(COMPANIES)
                    .document(employee.currentCompany)
                    .collection(EMPLOYEES)
                    .document(employee.firstName + " " + employee.lastName)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val doc = it.result
                            if (doc != null) {
                                if (doc.exists()) {
                                    employeeExists.value = true
                                }
                            }
                        }
                    }

                if (employeeExists.value == false) {
                    firebaseAuth.createUserWithEmailAndPassword(
                        employee.workEmail,
                        employee.password
                    ).await()

                    firebaseFirestore.collection(COMPANIES)
                        .document(employee.currentCompany)
                        .collection(EMPLOYEES)
                        .document(employee.firstName + " " + employee.lastName)
                        .set(employeeInfo, SetOptions.merge())
                        .await()

                    emit(DataState.data(data = "Employee authentication and creation successful"))
                } else {
                    emit(DataState.error(message = "Employee already exists already exists"))
                }

            }catch (e: Throwable) {
                emit(DataState.error(message = e.message.toString()))
            } catch (e: FirebaseException) {
                emit(DataState.error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}