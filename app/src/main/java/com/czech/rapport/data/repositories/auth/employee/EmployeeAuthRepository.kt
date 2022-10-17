package com.czech.rapport.data.repositories.auth.employee

import com.czech.rapport.data.models.EmployeeInfo
import com.czech.rapport.utils.states.DataState
import kotlinx.coroutines.flow.Flow

interface EmployeeAuthRepository {

    fun createEmployee(employee: EmployeeInfo): Flow<DataState<String>>
}