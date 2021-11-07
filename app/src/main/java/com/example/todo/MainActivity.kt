package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.taskFragment.TaskFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null){

            val fragment = TaskFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()




        }
    }
}