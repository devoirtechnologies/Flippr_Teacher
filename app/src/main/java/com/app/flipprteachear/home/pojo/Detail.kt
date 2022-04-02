package com.app.flipprteachear.home.pojo

data class Detail(
    val academy_name: String?=null,
    val avg_confid: Int?=null,
    val avg_grit: Int?=null,
    val avg_points: Int?=null,
    val avg_syllabus_done: Int?=null,
    val chapters: List<Chapter>?=null,
    val class_name: String?=null,
    val students: List<Student>?=null,
    val students_count: Int?=null,
    val subject_id: String?=null,
    val avg_homework: String?=null,
    val avg_syllabus_mastered: String?=null,
    val subject_name: String?=null
)