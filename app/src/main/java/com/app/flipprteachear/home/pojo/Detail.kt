package com.app.flipprteachear.home.pojo

data class Detail(
    val academy_name: String,
    val avg_confid: Int,
    val avg_grit: Int,
    val avg_points: Int,
    val avg_syllabus_done: Int,
    val chapters: List<Chapter>,
    val class_name: String,
    val students: List<Student>,
    val students_count: Int,
    val subject_id: String,
    val subject_name: String
)