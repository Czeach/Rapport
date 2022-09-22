package com.czech.rapport.data.repositories.auth.company

import android.util.Log
import com.czech.rapport.data.models.CompanyInfo
import com.czech.rapport.utils.Constants.COMPANIES
import com.czech.rapport.utils.states.DataState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompanyAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : CompanyAuthRepository {



    override suspend fun createCompany(company: CompanyInfo): Flow<DataState<String>> {
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
                firebaseAuth.createUserWithEmailAndPassword(
                    company.companyEmail,
                    company.companyPassword
                ).await()

                firebaseFirestore.collection(COMPANIES)
                    .document(company.companyName)
                    .set(companyInfo, SetOptions.merge())
                    .await()

                emit(DataState.data(data = "Company authentication and creation successful"))

            }catch (e: Throwable) {
                emit(DataState.error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}