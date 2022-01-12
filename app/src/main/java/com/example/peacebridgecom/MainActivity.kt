package com.example.peacebridgecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    //create a variable that stores the tasks added by a user.
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //1. Remove item from the list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that our data has changed
                adapter.notifyDataSetChanged()

                saveitems()
            }

        }

        listOfTasks.add("Do Laundry")
        listOfTasks.add("Go for a walk")

        loadItems()

        //Look up recyclerView in the Layout
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        //Attach the adapter to the recyclerview to populate items
        recyclerview.adapter = adapter
        // Set layout manager to position the items
        recyclerview.layoutManager = LinearLayoutManager(this)

        //set up the button and input field so that the user can enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)


        //Get a reference to the button
        //and then set an onclicklistener.
        findViewById<Button>(R.id.button).setOnClickListener {
            //1. Grab the text the user has inputted into @id/addTaskfield
            val userInputtedTask = inputTextField.text.toString()

            //2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            //Notify adapter that our data has been adapted.
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3. Reset text field
            inputTextField.setText("")

            saveitems() }
    }

    //save the data the user has inputted

    //save the data by writing and reading from a field

    //get the file we need
    fun getDataFile(): File {

        //Every line represents a specific task in our list in our task.
        return File(filesDir, "data.txt")
    }

    //load items by reading every line in our line
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    //save items by writing them into our data file
    fun saveitems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (iOException: IOException) {
            iOException.printStackTrace()
        }
    }
}





