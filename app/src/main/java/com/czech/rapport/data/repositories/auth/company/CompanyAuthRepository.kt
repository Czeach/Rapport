package com.czech.rapport.data.repositories.auth.company

import com.czech.rapport.utils.states.DataState
import kotlinx.coroutines.flow.Flow

interface CompanyAuthRepository {

    suspend fun createCompany()
}