package com.app.flipprteachear.home.pojo

import java.io.Serializable

data class PojoTeacherClass(
    val details: List<Detail>?=null,
    val message: String?=null
): Serializable