package com.example.todo.taskFragment

 import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
 import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.todo.DatePickerDialogFragment
import com.example.todo.R
import com.example.todo.database.Task
import com.example.todo.taskListFragment.KEY_ID
import com.example.todo.taskListFragment.TaskListFragment
import android.text.format.DateFormat
 import com.example.todo.taskListFragment.TaskListViewModel
 import java.util.*



const val Task_DATE_KEY = "taskDate"
class TaskFragment : Fragment(),DatePickerDialogFragment.DatePickerCallback {

    private val dateFormat = "EEE, MMM dd, yyyy"

    private lateinit var titleEditText: EditText
    private lateinit var detailEditText: EditText
    private lateinit var dateDueBtn: Button
    private lateinit var deleteTask : ImageView
    private lateinit var addBtn : Button

    private lateinit var task : Task

    private val fragmentViewModel by lazy { ViewModelProvider(this).get(TaskFragmentViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_task, container, false)


        titleEditText = view.findViewById(R.id.task_title)
        dateDueBtn = view.findViewById(R.id.task_date_due)
        detailEditText = view.findViewById(R.id.task_details)
        deleteTask = view.findViewById(R.id.task_deleted_iv)
        addBtn = view.findViewById(R.id.add_btn)



        dateDueBtn.apply {

            text = task.creationDate.toString()

        }

        return view
    }

    override fun onStart() {
        super.onStart()

        dateDueBtn.setOnClickListener {
            val args = Bundle()
            args.putSerializable(Task_DATE_KEY,task.creationDate)

            val datePicker = DatePickerDialogFragment()
            datePicker.arguments = args

            datePicker.setTargetFragment(this,0)

            datePicker.show(this.parentFragmentManager,"date picker")


        }


        val titleTextWatcher = object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //i will do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("ASRAR", p0.toString())
                task.title= p0.toString()


            }

            override fun afterTextChanged(p0: Editable?) {
                //i will do nothing
            }

        }

        val detailTextWatcher = object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //i will do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("ASRAR", p0.toString())
                  task.detail =p0.toString()

            }

            override fun afterTextChanged(p0: Editable?) {
                //i will do nothing
            }

        }

        titleEditText.addTextChangedListener(titleTextWatcher)
        detailEditText.addTextChangedListener(detailTextWatcher)

        deleteTask.setOnClickListener {

            fragmentViewModel.deleteTask(task)

            val args = Bundle()
            args.putSerializable(KEY_ID,task.id)

            val fragment = TaskListFragment()
            fragment.arguments = args

            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .addToBackStack(null)
                    .commit()
            }

        }

        addBtn.setOnClickListener {
            fragmentViewModel.saveUpdate(task)
            val fragment = TaskListFragment()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        task = Task()

        val taskId = arguments?.getSerializable(KEY_ID) as UUID
        fragmentViewModel.loadTask(taskId)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.taskLiveData.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                     task = it
                    titleEditText.setText(it.title)
                    detailEditText.setText(it.detail)
                    dateDueBtn.text = it.duoDate.toString()
                }
            }
        )
    }

    override fun onDateSelected(date: Date) {
        task.duoDate = date
        dateDueBtn.text = DateFormat.format(dateFormat,task.duoDate)
     }

    override fun onStop() {
        super.onStop()
        if(task.title.isEmpty()){
            fragmentViewModel.deleteTask(task)
        }else {
            fragmentViewModel.saveUpdate(task)
        }
    }
}