package com.example.todo.taskListFragment

 import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.CheckBox
 import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.Task
import com.example.todo.taskFragment.TaskFragment
 import java.util.*

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
                 val fragment = TaskFragment()
                fragment.arguments = args

                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .addToBackStack(null)
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

        val linearLayoutManager = LinearLayoutManager(context)
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
        private val overDueTask : TextView = itemView.findViewById(R.id.overdue_task)
        private val isCompletedCheckBox: CheckBox = itemView.findViewById(R.id.task_completed)

        init {
            itemView.setOnClickListener(this)
        }


        private val dateFormat = "EEE, MMM dd, yyyy"
        fun bind(task : Task){
            this.task = task
            titleTextView.text = task.title
            
            val currentDate = Date()


            if (task.duoDate != null) {
                dateTextView.text = DateFormat.format(dateFormat, task.duoDate)

                if (currentDate.after(task.duoDate)) {
                    if (task.isCompleted) {
                        overDueTask.visibility = View.GONE
                    }else{
                        overDueTask.visibility = View.VISIBLE
                     }

                } else {
                     overDueTask.visibility =  View.GONE

                }
            }else{
                dateTextView.text = " "
            }


            isCompletedCheckBox.isChecked = task.isCompleted

            isCompletedCheckBox.setOnCheckedChangeListener {  _, isChecked ->
                task.isCompleted = isChecked
                taskListViewModel.saveUpdate(task)
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
                        .addToBackStack(null)
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