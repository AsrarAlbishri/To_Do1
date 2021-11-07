package com.example.todo.taskListFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.Task

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


}