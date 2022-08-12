package com.czech.rapport.data.repositories.auth.company

import com.czech.rapport.data.models.CompanyInfo
import com.czech.rapport.utils.Constants.COMPANIES
import com.czech.rapport.utils.states.DataState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CompanyAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : CompanyAuthRepository {

    override suspend fun createCompany(company: CompanyInfo): Flow<DataState<String>> {
        return flow<DataState<String>> {
            emit(DataState.loading())

            firebaseAuth.createUserWithEmailAndPassword(
                company.companyEmail.toString(),
                company.companyPassword.toString()
            ).addOnCompleteListener { task ->
                try {
                    when (task.isSuccessful) {
                        true -> {
                            flow {
                                emit(DataState.data(data = "Authentication successful"))

                                val companyInfo = CompanyInfo(
                                    id = firebaseAuth.currentUser?.uid,
                                    companyName = company.companyName,
                                    companyEmail = company.companyEmail,
                                    industryType = company.industryType,
                                    companySize = company.companySize,
                                    headquarterAddress = company.headquarterAddress,
                                    nameOfRegistrar = company.nameOfRegistrar,
                                    positionOfRegistrar = company.positionOfRegistrar,
                                    companyDescription = company.companyDescription,
                                    companyPassword = company.companyPassword
                                )

                                firebaseFirestore.collection(COMPANIES)
                                    .document(company.companyName!!)
                                    .set(companyInfo)
                                    .addOnSuccessListener {
                                        flow {
                                            emit(DataState.data(data = "Successfully created employee"))
                                        }.flowOn(Dispatchers.IO)
                                    }
                                    .addOnFailureListener {
                                        flow<DataState<String>> {
                                            emit(DataState.error(message = "Employee creation failed"))
                                        }.flowOn(Dispatchers.IO)
                                    }
                            }.flowOn(Dispatchers.IO)
                        }
                        false -> {
                            flow<DataState<String>> {
                                emit(DataState.error(message = "Authentication failed"))
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