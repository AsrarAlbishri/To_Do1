package com.example.todo.taskListFragment

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.Task
import com.example.todo.taskFragment.TaskFragment

const val KEY_ID = "myTaskId"
class TaskListFragment : Fragment() {

    private lateinit var taskRecyclerView: RecyclerView

    val taskListViewModel by lazy { ViewModelProvider(this).get(TaskListViewModel::class.java) }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.fragment_list_menu,menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            R.id.new_task ->  {
                val task = Task()
                taskListViewModel.addTask(task)

                val args = Bundle()
                args.putSerializable(KEY_ID,task.id)
                // كود الانتقال من fragment الى fragment
                val fragment = TaskFragment()
                fragment.arguments = args

                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .addToBackStack(null) //عشان لما يوديني على الفراقمنت الثانيه وبعدين اقدر ارجع للاولى
                        .commit()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_task_list, container, false)

        taskRecyclerView = view.findViewById(R.id.task_recycler_view)

        val linearLayoutManager = LinearLayoutManager(context) // سوينا اوبجكت من linear
        taskRecyclerView.layoutManager = linearLayoutManager

        return view
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        taskListViewModel.liveDataTasks.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        }
        )
    }

    private fun updateUI(tasks:List<Task> ){
        val taskAdapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = taskAdapter
    }

    private inner class TaskHolder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener{

        private lateinit var task: Task

        private val titleTextView: TextView = itemView.findViewById(R.id.task_title_item)
        private val dateTextView: TextView = itemView.findViewById(R.id.task_date_item)
        private  val isCompletedImageView: ImageView = itemView.findViewById(R.id.is_completed_iv)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(task : Task){
            this.task = task
            titleTextView.text = task.title
            dateTextView.text = task.date.toString()
            isCompletedImageView.visibility = if (task.isCompleted){
                View.VISIBLE
            }else{
                View.GONE
            }

        }

        override fun onClick(v: View?) {

            if (v == itemView){
                val args = Bundle()

                args.putSerializable(KEY_ID,task.id)

                val fragment = TaskFragment()
                fragment.arguments = args

                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .addToBackStack(null)   //يعني يحافظ على الفراقمنت اللي قبل لما اضغط زر رجوع وارجعلها القاها موجوده
                        .commit()
                }
            }

        }

    }

    private inner class TaskAdapter(var tasks:List<Task>):RecyclerView.Adapter<TaskHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.list_item_task,parent,false)

              return TaskHolder(view)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
         }

        override fun getItemCount(): Int = tasks.size
    }

}