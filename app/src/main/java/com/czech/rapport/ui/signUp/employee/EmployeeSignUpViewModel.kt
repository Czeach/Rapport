package com.czech.rapport.ui.signUp.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.rapport.data.models.EmployeeInfo
import com.czech.rapport.data.repositories.auth.employee.EmployeeAuthRepository
import com.czech.rapport.utils.states.EmployeeSignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeSignUpViewModel @Inject constructor(
    private val employeeAuthRepository: EmployeeAuthRepository
): ViewModel() {

    val employeeSignUpState = MutableStateFlow<EmployeeSignUpState?>(null)

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