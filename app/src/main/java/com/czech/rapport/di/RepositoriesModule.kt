package com.czech.rapport.di

import com.czech.rapport.data.repositories.auth.company.CompanyAuthRepository
import com.czech.rapport.data.repositories.auth.company.CompanyAuthRepositoryImpl
import com.czech.rapport.data.repositories.auth.employee.EmployeeAuthRepository
import com.czech.rapport.data.repositories.auth.employee.EmployeeAuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object RepositoriesModule {

    @[Provides Singleton]
    fun provideCompanyAuthRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): CompanyAuthRepository {
        return CompanyAuthRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firebaseFirestore
        )
    }

    @[Provides Singleton]
    fun provideEmployeeAuthRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): EmployeeAuthRepository {
        return EmployeeAuthRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firebaseFirestore
        )
    }
}