package com.czech.rapport.data.models

data class CompanyInfo(
    val id: String? = null,
    val companyName: String,
    val companyEmail: String,
    val industryType: String,
    val companySize: String,
    val headquarterAddress: String,
    val nameOfRegistrar: String,
    val positionOfRegistrar: String,
    val companyDescription: String,
    val companyPassword: String
)
