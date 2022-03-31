package com.app.flipprteachear.home.pojo.topicDetail

data class TopicDetail(
    val chapter_id: String,
    val chapter_image: String,
    val chapter_name: String,
    val cluster_id: String,
    val ques_count: Int,
    val school_class_course_id: String,
    val school_course_structure_id: String,
    val school_id: String,
    var session_total_time: Float,
    var spend_session_time: Float,
    val subject_id: String,
    val subject_name: String,
    val teacher_activity_status: String,
    val topic_id: String,
    val topic_image: String,
    val topic_name: String
)