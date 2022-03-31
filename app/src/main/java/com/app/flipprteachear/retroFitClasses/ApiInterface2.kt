package com.app.flipprteachear.retroFitClasses

import com.app.flipprteachear.LoginRegistration.pojo.PojoLogin
import com.app.flipprteachear.home.pojo.PojoTeacherClass
import com.app.flipprteachear.home.pojo.liveModel.PojoTeacherCreateLive
import com.app.flipprteachear.home.pojo.liveQuesPojo.PojMcqQues
import com.app.flipprteachear.home.pojo.topicDetail.*
import com.app.flipprteachear.home.pojo.userDetail.PojoUserDetail
import retrofit2.http.*
import java.util.HashMap


interface ApiInterface2 {
    @FormUrlEncoded
    @POST("teacher_home_classes.php")
    suspend fun teacher_home_classes(@Header("Authorization") authtoken: String, @Field("user_id") userID: String): PojoTeacherClass

     // @FormUrlEncoded
    @POST("teacher_topics_sessions.php")
    suspend fun getTopicDetail(@Header("Authorization") authtoken: String, @Body model: ModelTopicDetail ): PojoTopicDetail

     // @FormUrlEncoded
    @POST("teacher_update_course_structure.php")
    suspend fun getTopicCheckBox(@Header("Authorization") authtoken: String, @Body model: ModelTopicCheckBox ): PojoAllUpdate

    @POST("teacher_create_live_session.php")
    suspend fun teacher_create_live_session(@Header("Authorization") authtoken: String, @Body model: ModelTeacherCreateLiveSession ): PojoTeacherCreateLive

    @POST("teacher_live_questions.php")
    suspend fun getLiveQues1(@Header("Authorization") authtoken: String, @Body  model: ModelLiveDetail): PojMcqQues

    @POST("get_users_details.php")
    suspend fun get_users_details(@Header("Authorization") token: String, @Body model: HashMap<String, String>): PojoUserDetail

    @POST("live_submit_question_users.php")
    suspend fun live_submit_question_users(@Header("Authorization") token: String, @Body model: HashMap<String, String>): PojoUserDetail

    @POST("logout.php")
    suspend fun logoutApi(@Header("Authorization") token: String, @Body model: HashMap<String, String>): PojoUserDetail


}