package com.example.insightcheck_hr.dataClass

data class user(
    val name : String ?= null,
    val leaveMessage : String ?= null,
    val startDate: String ?= null,
    val endDate: String ?= null,
)
