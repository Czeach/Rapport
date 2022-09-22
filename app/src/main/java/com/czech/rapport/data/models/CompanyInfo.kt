package com.czech.rapport.data.models

data class CompanyInfo(
    val id: String? = null,
    val companyName: String,
    val companyEmail: String,
    val industryType: String? = null,
    val companySize: String? = null,
    val headquarterAddress: String? = null,
    val nameOfRegistrar: String? = null,
    val positionOfRegistrar: String? = null,
    val companyDescription: String? = null,
    val companyPassword: String
)
