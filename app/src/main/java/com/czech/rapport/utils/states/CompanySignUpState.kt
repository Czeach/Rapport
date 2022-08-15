package com.czech.rapport.utils.states

sealed class CompanySignUpState {
    object Loading : CompanySignUpState()
    data class Success(val data: String?) : CompanySignUpState()
    data class Error(val message: String) : CompanySignUpState()
}