package com.czech.rapport.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.rapport.data.models.CompanyInfo
import com.czech.rapport.data.models.EmployeeInfo
import com.czech.rapport.data.repositories.auth.company.CompanyAuthRepository
import com.czech.rapport.data.repositories.auth.employee.EmployeeAuthRepository
import com.czech.rapport.utils.states.CompanySignUpState
import com.czech.rapport.utils.states.EmployeeSignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val companyAuthRepository: CompanyAuthRepository,
    private val employeeAuthRepository: EmployeeAuthRepository
) : ViewModel() {

    val companySignUpState = MutableStateFlow<CompanySignUpState?>(null)
    val employeeSignUpState = MutableStateFlow<EmployeeSignUpState?>(null)

    fun companySignUp(company: CompanyInfo) {
        viewModelScope.launch {
            companyAuthRepository.createCompany(company).collect {
                when {
                    it.isLoading -> {
                        companySignUpState.value = CompanySignUpState.Loading
                    }
                    it.data == null -> {
                        companySignUpState.value = CompanySignUpState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { msg ->
                            companySignUpState.value = CompanySignUpState.Success(msg)
                        }
                    }
                }
            }
        }
    }

    fun createEmployee(employee: EmployeeInfo) {
        viewModelScope.launch {
            employeeAuthRepository.createEmployee(employee).collect {
                when {
                    it.isLoading -> {
                        employeeSignUpState.value = EmployeeSignUpState.Loading
                    }
                    it.data == null -> {
                        employeeSignUpState.value = EmployeeSignUpState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { msg ->
                            employeeSignUpState.value = EmployeeSignUpState.Success(data = msg)
                        }
                    }
                }
            }
        }
    }
}