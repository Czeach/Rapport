package com.czech.rapport.data.repositories.auth.login

import com.czech.rapport.data.models.CompanyInfo
import com.czech.rapport.utils.states.DataState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun login(email: String, password: String): Flow<DataState<String>>

}