package com.app.flipprteachear.home.view.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.flipprteachear.R
import com.app.flipprteachear.databinding.FragmentHome1Binding
import com.app.flipprteachear.home.ForClassPageChange
import com.app.flipprteachear.home.ForStartLive
import com.app.flipprteachear.home.view.adapter.ClassPageAdapter
import com.app.flipprteachear.home.view.adapter.chapterAdapter
import com.app.flipprteachear.home.view.adapter.classStudentAdapter
import com.app.flipprteachear.home.pojo.PojoTeacherClass
import com.app.flipprteachear.home.pojo.Student
import com.app.flipprteachear.home.view.viewModel.MainViewModel
import com.app.flipprteachear.retroFitClasses.apiCalls
import com.app.flipprteachear.utillsa.PreferenceManager
import com.app.flipprteachear.utillsa.utills
import com.hubwallet.utillss.ResultWrapper
import kotlinx.android.synthetic.main.fragment_home1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeFragment : Fragment(), ForClassPageChange {
      lateinit var viewModel: MainViewModel
      lateinit var viewBinding:FragmentHome1Binding
      private var pojoTeacherClass: PojoTeacherClass?=null
      private var pref: PreferenceManager?=null
      private var startLive : ForStartLive?=null
      var possn =0
       var poss =0

        private val studentAdapter by lazy {
            classStudentAdapter(1,startLive!!)
        }


        private val chapterAdapterr by lazy {
            chapterAdapter(this, requireActivity(), pref)
        }
//        private val classPageAdapter by lazy {
//            ClassPageAdapter(this)
//        }
    private val classPageAdapter by lazy {
            ClassPageAdapter(this)
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startLive =   context as ForStartLive
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View {
        // Inflate the layout for this fragment
        viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fragment_home1, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        pref = PreferenceManager(requireContext())
        viewBinding.rvStudents.setLayoutManager(GridLayoutManager(activity, 4))
        viewBinding.rvStudents.adapter = studentAdapter
        viewBinding.rvUchapters.adapter = chapterAdapterr
        //viewBinding.rvClassPagesDots.adapter =classPageAdapter
        viewBinding.rvClassPages.adapter =classPageAdapter
        viewBinding.progressBar.visibility = View.GONE
        onClickedListners()
        getHomeApi()
        viewBinding.ivRefresh.setOnClickListener {

            viewBinding.ivRefresh.clearAnimation()
            val anim = RotateAnimation(30f, 360f, (viewBinding.ivRefresh.getWidth() / 2).toFloat(), (viewBinding.ivRefresh.getHeight() / 2).toFloat())
            anim.fillAfter = true
            anim.repeatCount = 0
            anim.duration = 1000
            viewBinding. ivRefresh.startAnimation(anim)
            getHomeApi()
        }
        return viewBinding.root
    }
    private fun getHomeApi() {
            viewModel.teacher_home_classes(pref!!.getValueString("token"), pref!!.getValueString("userId")).observe(viewLifecycleOwner,{
                updateUI(it)
            })

           //teacher_home_classes()
        }

    fun teacher_home_classes() {
        viewBinding.progressBar.setVisibility(View.VISIBLE)
        val api = apiCalls()
        val callApi: Call<PojoTeacherClass> = api.teacher_home_classes(pref!!.getValueString("token"), pref!!.getValueString("userId"))
        callApi.enqueue(object : Callback<PojoTeacherClass> {
            override fun onResponse(call: Call<PojoTeacherClass>, response: Response<PojoTeacherClass>) {
                viewBinding.progressBar.setVisibility(View.GONE)
                if (response.body()!!.message.equals("success")) {
                    pojoTeacherClass = response.body()!!
                    setTeacherClassData(response.body()!!)

                }
            }

            override fun onFailure(call: Call<PojoTeacherClass>, t: Throwable) {
                viewBinding.progressBar.setVisibility(View.GONE)

            }
        })
    }

        private fun onClickedListners() {
        with(viewBinding){
            tvPoints.setOnClickListener { updateBtns_onType(1, tvPoints) }
            tvHomeWork.setOnClickListener { updateBtns_onType(2, tvHomeWork) }
            tvSyllabus.setOnClickListener { updateBtns_onType(3, tvSyllabus) }
            tvConfidence.setOnClickListener { updateBtns_onType(4, tvConfidence) }
            tvSyllMaster.setOnClickListener { updateBtns_onType(5, tvSyllMaster) }
            llSeeAll.visibility= View.GONE
            llSeeAll.setOnClickListener {
                val bundle =Bundle()
                bundle.putSerializable("pojoTeacherClass",pojoTeacherClass)
                bundle.putInt("poss_",possn)
                getFragemtent(ChapterListFragment(),bundle,"chapterList")
            }
        }
    }

    private fun updateBtns_onType(type:Int, tv: TextView) {
        with(viewBinding){
            tvPoints.background = ResourcesCompat.getDrawable(resources, R.drawable.round_boundary_navi_new, null)
            tvHomeWork.background = ResourcesCompat.getDrawable(resources, R.drawable.round_boundary_navi_new, null)
            tvSyllabus.background = ResourcesCompat.getDrawable(resources, R.drawable.round_boundary_navi_new, null)
            tvConfidence.background = ResourcesCompat.getDrawable(resources, R.drawable.round_boundary_navi_new, null)
            tvSyllMaster.background = ResourcesCompat.getDrawable(resources, R.drawable.round_boundary_navi_new, null)
            tv.background = ResourcesCompat.getDrawable(resources, R.drawable.round_skyblue, null)
        }

        try{
            pojoTeacherClass?.details!![poss].let {
                if(type == 1){
                Collections.sort(it.students,StuPointComparator)
                }else if(type == 2  ){
                Collections.sort(it.students,StuPointComparator2)
                }else if(type == 3){
                Collections.sort(it.students,StuPointComparator3)
                }else if(type == 4){
                Collections.sort(it.students,StuPointComparator4)
                }else if(type == 5){
                Collections.sort(it.students,StuPointComparator5)
                }
                studentAdapter.updateListType(it.students!!)
            }
            studentAdapter.updateType(type)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun updateUI(_state: ResultWrapper<Any>?) {
            when (_state) {

                is ResultWrapper.Loading -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                }

                is ResultWrapper.NetworkError -> {
                    viewBinding.progressBar.visibility = View.GONE
                }
                is ResultWrapper.GenericError -> {
                    viewBinding.progressBar.visibility = View.GONE
                    _state.error?.error?.let {
                        Toast.makeText(requireActivity(), "err " + it, Toast.LENGTH_LONG).show()
                    }
                }
                is ResultWrapper.Success -> {

                    when(_state.data){
                        is PojoTeacherClass ->{
                            _state.data.let {
                                if (it.message.equals("success")){
                                    pojoTeacherClass = it
                                    setTeacherClassData(it)
                                    tv_points.performClick()

                                }
                            }
                        }
                        /*is PojoLogin->{
                            _state.data.let {
                                if (it.status.equals(1)){
                                    Toast.makeText(activity,it.message, Toast.LENGTH_SHORT).show()
                              }
                            }
                        }*/
                    }
                    viewBinding.progressBar.visibility = View.GONE
                }
            }
        }

    private fun setTeacherClassData(it: PojoTeacherClass) {

        with(viewBinding){
           rvClassPages.adapter =classPageAdapter
            try {
                PagerSnapHelper().attachToRecyclerView(rvClassPages)
            }catch (e:Exception){e.printStackTrace()}


            val layoutManager = rvClassPages.layoutManager as LinearLayoutManager

            it.details?.let {
                classPageAdapter.updateList(it)
                it[0].students.let {
                    studentAdapter.updateListType(it!!)
                }
                it[0].chapters.let {
                    chapterAdapterr.updateListType(it!!)
                }

            }

            // viewBinding.rvUchapters.adapter =chapterAdapterr
            rvClassPages.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    try {
                          poss = layoutManager.findFirstCompletelyVisibleItemPosition()
                        Log.e("TAG", "onScrolled: $poss")
                        possn = poss.plus(1)
                         tvTitleValue.text = "("+possn +" of " +pojoTeacherClass?.details?.size+")"
                        if(poss!=-1)
                        it.details?.let {
                            if(!it[poss].students.isNullOrEmpty())
                                studentAdapter.updateListType(it[poss].students!!)
                           if(!it[poss].chapters.isNullOrEmpty())
                               chapterAdapterr.updateListType(it[poss].chapters!!)
                        tvClass.text =getString(R.string.class_performance_in_physics,it[poss].subject_name )
                        }

                    }catch (e:Exception){e.printStackTrace()}

                }

            })
        }
    }

    override fun changeClass(poss: Int) {
       var poss = poss+1
       viewBinding.tvTitleValue.setText("("+possn +" of " +pojoTeacherClass?.details?.size+")")
    }

    override fun getTopicPage(schoolCourseStructureId: String) {
        var bundle = Bundle()
        bundle.putString("schoolCourseStructureId",schoolCourseStructureId)
        Log.d("schoolCourseStructureId",schoolCourseStructureId)
        getFragemtent(TopicListFragment(), bundle, "topicList")
//        val ft = activity?.supportFragmentManager!!.beginTransaction()
//        utills.replacefrag_withBackStack(ft, TopicListFragment(), Bundle(), "topicList")
    }
   fun getFragemtent(fr:Fragment, bundle :Bundle, name:String){
        val ft = activity?.supportFragmentManager!!.beginTransaction()
        utills.replacefrag_withBackStack(ft, fr, bundle, "topicList")
    }

    var StuPointComparator: Comparator<Student> =
        Comparator<Student> { s1, s2 ->

            var  point1 = s1.total_points ?:"0"
            var  point2 = s2.total_points ?:"0"
            val d1 = java.lang.Double.valueOf(point1)
            val d2 = java.lang.Double.valueOf(point2)

            return@Comparator java.lang.Double.compare(d2, d1)
        }

    var StuPointComparator3: Comparator<Student> =
        Comparator<Student> { s1, s2 ->
            val d1 =  s1.syllabus_comp!!
            val d2 =  s2.syllabus_comp!!
            return@Comparator d2.compareTo(d1)
        }
    var StuPointComparator2: Comparator<Student> =
        Comparator<Student> { s1, s2 ->
            val d1 =  s1.homework_comp!!
            val d2 =   s2.homework_comp!!
            return@Comparator d2.compareTo(d1)
        }

    var StuPointComparator4: Comparator<Student> =
        Comparator<Student> { s1, s2 ->
            val d1 = java.lang.Double.valueOf(s1.avg_confid!!)
            val d2 = java.lang.Double.valueOf(s2.avg_confid!!)

            return@Comparator java.lang.Double.compare(d2, d1)

        }

    var StuPointComparator5: Comparator<Student> =
        Comparator<Student> { s1, s2 ->
            val d1 =  s1.syllabus_mastered!!
            val d2 =  s2.syllabus_mastered!!

            return@Comparator d2.compareTo(d1)

        }
// pojoTeacherClass = response.body()!!
//                    setTeacherClassData(response.body()!!)
    override fun onResume() {
        super.onResume()
        Log.e("TAG", "onResume: ", )
    }



    override fun onStart() {
        super.onStart()
        Log.e("TAG", "onResume:onStart: ", )
       // if(pojoTeacherClass!=null)
         pojoTeacherClass?.let {
             setTeacherClassData(pojoTeacherClass!!)
            }

    }


}