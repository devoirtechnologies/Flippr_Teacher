package com.app.flipprteachear.home.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.flipprteachear.R
import com.app.flipprteachear.home.ForLivePageChange
import com.app.flipprteachear.home.pojo.topicDetail.TopicDetail

public class TopicListAdapter(val classPage: ForLivePageChange) : RecyclerView.Adapter<TopicListAdapter.custom>() {
    lateinit var context : Context
    private var topicList: List<TopicDetail>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): custom {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.res_topic_list_row, parent, false)
        context =parent.context
        return custom(view)
    }

    override fun onBindViewHolder(holder: custom, position: Int) {
        holder.tv_number.text = "" + (position + 1)
         try {
             with(holder){

                 topicList!![position].let {
                     iv_image.load(it.topic_image?:""){
                         placeholder(R.drawable.image)
                     }
                     if (it.teacher_activity_status.equals("1", false))
                         tv_chapterStatus.text = "Ongoing in class"
                     else if (it.teacher_activity_status.equals("0",false))
                         tv_chapterStatus.text = "Done in class"
                     else if (it.teacher_activity_status!!.isEmpty())
                         tv_chapterStatus.text = "Not started in class"

                      tv_subjectTopic.text  =   it.topic_name?:""
                     var time = String.format("%.1f",it.session_total_time!!/60f)
                     Log.e("TAG", "onBindViewHolder: $time", )
                      tv_topicn_mins.text  =   context.resources.getString(R.string._15_min_78_questions,  (if(time.equals("0")) "1" else time)+"", it.ques_count.toString())
                     cbx.isChecked = if(it.teacher_activity_status.equals("1", false) ) true
                                      else false
                     cbx.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
                         override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                             if(isChecked) classPage.checkBoxUpdate("1", it.school_course_structure_id!!)
                             else classPage.checkBoxUpdate("0", it.school_course_structure_id!!)
                         }
                     })
                 }


                 itemView.setOnClickListener {
                      classPage.golivePage(topicList!![position])
                 }
                 tv_live.setOnClickListener {
                     //classPage.endlivePage()
                     classPage.golivePage(topicList!![position])
                 }

             }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return if(topicList!=null) topicList!!.size else 0
    }



    fun updateList(topicList: List<TopicDetail>) {
         this.topicList = topicList
        notifyDataSetChanged()
    }

    inner class custom(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_number: TextView
        var tv_live: TextView
        var tv_subjectTopic: TextView
        var tv_chapterStatus: TextView
        var tv_topicn_mins: TextView
       // var cardimage: CardView
        var iv_image: ImageView
        var cbx: CheckBox

        init {
            tv_number = itemView.findViewById(R.id.tv_number)
            tv_live = itemView.findViewById(R.id.tv_live)
            iv_image = itemView.findViewById(R.id.iv_topicImage)
            tv_subjectTopic = itemView.findViewById(R.id.tv_subjectTopic)
            tv_topicn_mins = itemView.findViewById(R.id.tv_topicn_mins)
            tv_chapterStatus = itemView.findViewById(R.id.tv_chapterStatus)
            cbx = itemView.findViewById(R.id.cbx)

           // cardimage = itemView.findViewById(R.id.cardimage)
        }
    }


}