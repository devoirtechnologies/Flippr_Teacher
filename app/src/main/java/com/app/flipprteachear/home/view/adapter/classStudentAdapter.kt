package com.app.flipprteachear.home.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.flipprteachear.R
import com.app.flipprteachear.home.pojo.Student

public class classStudentAdapter(var type: Int) : RecyclerView.Adapter<classStudentAdapter.custom>() {
    lateinit var context : Context
    private var students: List<Student>?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): custom {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.res_students_class, parent, false)
        context =parent.context
        return custom(view)
    }

    override fun onBindViewHolder(holder: custom, position: Int) {
        holder.tv_number.text = "" + (position + 1)
        try {
            students!![position].let {
//            Glide.with(context)
//                .load( it.image)
//                .placeholder(R.drawable.image)
//                .into(holder.iv_image)

                 try {
                holder.iv_image.load(it.image){
                   // crossfade(true)
                    placeholder(R.drawable.image)
                   // transformations()
                }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
         if (type == 1) {
             if (students!![position].total_points == null) {
                 holder.tv_number2.text = "0°P"
             } else {
                 holder.tv_number2.setText(
                     students!![position].total_points.toDouble().toInt().toString() + "°P"
                 )
             }
         } else if (type == 2) {
             holder.tv_number2.text =
                 String.format("%.1f", students!![position].avg_confid).toString() + "% S"
         }else if (type == 3) {
             holder.tv_number2.text =
                 String.format("%.1f", students!![position].syllabus_comp).toString() + "% S"
         }else if (type == 5){
             holder.tv_number2.text =
                 String.format("%.1f", students!![position].syllabus_mastered).toString() + " S"
         }
         else {
             holder.tv_number2.text =
                 String.format("%.1f", students!![position].avg_confid).toString() + " C"
         }
         holder.tv_studentName.setText(students!![position].first_name.toString() + " ")

    }

    override fun getItemCount(): Int {
        return if(students!=null) students!!.size else 0
    }
    fun updateListType(students: List<Student>) {
        this.students = students
        notifyDataSetChanged()
    }
    fun updateType(type:Int) {
        this.type = type
        notifyDataSetChanged()
    }

    inner class custom(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_number: TextView
        var tv_number2: TextView
        var tv_studentName: TextView
        var cardimage: CardView
        var iv_image: ImageView

        init {
            tv_number = itemView.findViewById(R.id.tv_number)
            tv_number2 = itemView.findViewById(R.id.tv_number2)
            tv_studentName = itemView.findViewById(R.id.tv_studentName)
            iv_image = itemView.findViewById(R.id.iv_image)
            cardimage = itemView.findViewById(R.id.cardimage)
        }
    }
}