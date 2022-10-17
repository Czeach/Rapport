package com.czech.rapport.data.repositories.auth.company

import com.czech.rapport.data.models.CompanyInfo
import com.czech.rapport.utils.Constants.COMPANIES
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

class CompanyAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : CompanyAuthRepository {

    private val companyExists = MutableStateFlow<Boolean?>(false)

    override fun createCompany(company: CompanyInfo): Flow<DataState<String>> {
        return flow<DataState<String>> {
            emit(DataState.loading())

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

            try {
                firebaseFirestore.collection(COMPANIES)
                    .document(company.companyEmail)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val doc = it.result
                            if (doc != null) {
                                if (doc.exists()) {
                                    companyExists.value = true
                                }
                            }
                        }
                    }

                if (companyExists.value == false) {
                    firebaseAuth.createUserWithEmailAndPassword(
                        company.companyEmail,
                        company.companyPassword
                    ).await()

                    firebaseFirestore.collection(COMPANIES)
                        .document(company.companyEmail)
                        .set(companyInfo, SetOptions.merge())
                        .await()

                    emit(DataState.data(data = "Company authentication and creation successful"))

                } else {
                    emit(DataState.error(message = "Company already exists"))
                }

            } catch (e: Throwable) {
                emit(DataState.error(message = e.message.toString()))
            } catch (e: FirebaseException) {
                emit(DataState.error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}