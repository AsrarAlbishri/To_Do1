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
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.database.Task


class TaskFragment : Fragment() {


    private lateinit var titleEditText: EditText
    private lateinit var detailEditText: EditText
    private lateinit var dateCreateBtn:Button
    private lateinit var dateDueBtn:Button
    private lateinit var isCompletedCheckBox:CheckBox

    private lateinit var task : Task

    private val fragmentViewModel by lazy { ViewModelProvider(this).get(TaskFragmentViewModel::class.java) }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_task, container, false)


        titleEditText = view.findViewById(R.id.task_title)
        dateCreateBtn = view.findViewById(R.id.task_create_date)
        dateDueBtn = view.findViewById(R.id.task_date_due)
        isCompletedCheckBox = view.findViewById(R.id.task_completed)
        detailEditText = view.findViewById(R.id.task_details)



        dateCreateBtn.apply {

            text = task.date.toString()

        }

        dateDueBtn.apply {

            text = task.date.toString()

        }



        return view
    }

    override fun onStart() {
        super.onStart()


        val textWatcher = object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //i will do nothing

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("ASRAR", p0.toString())
                task.title= p0.toString()
                task.detail= p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                //i will do nothing
            }

        }

        titleEditText.addTextChangedListener(textWatcher)
        detailEditText.addTextChangedListener(textWatcher)

        isCompletedCheckBox.setOnCheckedChangeListener {  _, isChecked ->
            task.isCompleted = isChecked
        }

    }


}