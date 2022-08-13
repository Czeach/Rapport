package com.czech.rapport.utils.states

sealed class EmployeeSignUpState {
    object Loading : EmployeeSignUpState()
    data class Success(val data: String?) : EmployeeSignUpState()
    data class Error(val message: String) : EmployeeSignUpState()
}
