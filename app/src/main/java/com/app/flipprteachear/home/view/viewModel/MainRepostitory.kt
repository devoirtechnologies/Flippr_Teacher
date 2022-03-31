package com.app.flipprteachear.home.view.viewModel

import com.app.flipprteachear.home.pojo.PojoTeacherClass
import com.app.flipprteachear.home.pojo.topicDetail.*
import com.app.flipprteachear.home.pojo.userDetail.PojoUserDetail
import com.app.flipprteachear.retroFitClasses.ApiInterface2
import java.util.HashMap


class MainRepostitory(var api: ApiInterface2) {

    suspend fun teacher_home_classes(token: String, userID: String): PojoTeacherClass {
       return api.teacher_home_classes(token, userID)
    }

    suspend fun getTopicDetail(token: String, model: ModelTopicDetail): PojoTopicDetail {
        return api.getTopicDetail(token, model)
    }

    suspend fun getTopicCheckBox(token: String, model: ModelTopicCheckBox): PojoAllUpdate {
        return api.getTopicCheckBox(token, model)
    }

    suspend fun logoutApi(token: String, model: HashMap<String, String>): PojoUserDetail {
        return api.logoutApi(token, model)
    }

  /*  suspend fun getLiveQues1(token: String, model: ModelLiveDetail): Pojo {
        return api.getLiveQues1(token, model)
    }*/


}