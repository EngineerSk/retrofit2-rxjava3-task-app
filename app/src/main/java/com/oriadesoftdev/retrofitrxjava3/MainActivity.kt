package com.oriadesoftdev.retrofitrxjava3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.oriadesoftdev.retrofitrxjava3.data.response.TaskResponse
import com.oriadesoftdev.retrofitrxjava3.ui.TaskViewModel
import com.oriadesoftdev.retrofitrxjava3.ui.adapter.TaskRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mListAdapter: TaskRecyclerViewAdapter
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            ).get()
        observers()
        mRecyclerView = findViewById(R.id.taskListRecyclerView)
        mProgressBar = findViewById(R.id.progressBar)
        findViewById<ExtendedFloatingActionButton>(R.id.add_fab).setOnClickListener {
            addTask()
        }
        mListAdapter = TaskRecyclerViewAdapter({
            editTask(it)
        }) {
            deleteTask(it)
        }
        mRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mListAdapter
        }
    }

    private fun observers() {
        taskViewModel
            .apply {
                isLoadingLiveData.observe(this@MainActivity) {
                    mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
                isSuccessLiveData.observe(this@MainActivity) {
                    Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
                }
                errorLiveData.observe(this@MainActivity) {
                    Log.e(TAG, "observers: $it")
                }
                taskListLiveData.observe(this@MainActivity) {
                    mListAdapter.setTaskList(it)
                    mRecyclerView.post {
                        mRecyclerView.scrollToPosition(0)
                    }
                }
            }
    }

    private fun addTask() {
        MaterialDialog(this)
            .apply {
                cornerRadius(8f)
                cancelable(false)
                customView(R.layout.add_task_dialog)
                positiveButton(R.string.action_save) {
                    val customView = it.getCustomView()
                    taskViewModel.apply {
                        taskUserIdLiveData.value = 2
                        taskTitleLiveData.value =
                            customView.findViewById<TextInputEditText>(R.id.titleTextInputEditText).text?.toString()
                                ?: ""
                        taskBodyLiveData.value =
                            customView.findViewById<TextInputEditText>(R.id.bodyTextInputEditText).text?.toString()
                                ?: ""
                        taskNoteLiveData.value =
                            customView.findViewById<TextInputEditText>(R.id.noteTextInputEditText).text?.toString()
                                ?: ""
                        taskStatusLiveData.value =
                            customView.findViewById<TextInputEditText>(R.id.statusTextInputEditText).text?.toString()
                                ?: ""
                    }
                    taskViewModel.addTask()
                }
                negativeButton(R.string.action_cancel) {
                    it.dismiss()
                }
                show()
            }
    }

    private fun editTask(data: TaskResponse.TaskResponseItem) {
        MaterialDialog(this)
            .apply {
                cornerRadius(8f)
                cancelable(false)
                customView(R.layout.add_task_dialog)
                taskViewModel.taskIdLiveData.value = data.id
                taskViewModel.taskUserIdLiveData.value = data.userId
                getCustomView().apply {
                    findViewById<TextInputEditText>(R.id.titleTextInputEditText).setText(data.title)
                    findViewById<TextInputEditText>(R.id.bodyTextInputEditText).setText(data.body)
                    findViewById<TextInputEditText>(R.id.noteTextInputEditText).setText(data.note)
                    findViewById<TextInputEditText>(R.id.statusTextInputEditText).setText(data.status)
                }
                positiveButton(R.string.action_save) {
                    val customView = it.getCustomView()
                    taskViewModel.apply {
                        taskTitleLiveData.value =
                            customView.findViewById<TextInputEditText>(R.id.titleTextInputEditText).text?.toString()
                                ?: ""
                        taskBodyLiveData.value =
                            customView.findViewById<TextInputEditText>(R.id.bodyTextInputEditText).text?.toString()
                                ?: ""
                        taskNoteLiveData.value =
                            customView.findViewById<TextInputEditText>(R.id.noteTextInputEditText).text?.toString()
                                ?: ""
                        taskStatusLiveData.value =
                            customView.findViewById<TextInputEditText>(R.id.statusTextInputEditText).text?.toString()
                                ?: ""
                    }
                    taskViewModel.editTask()
                }
                negativeButton(R.string.action_cancel) {
                    it.dismiss()
                }
                show()
            }
    }

    private fun deleteTask(data: TaskResponse.TaskResponseItem) {
        MaterialDialog(this)
            .apply {
                cornerRadius(8f)
                cancelable(false)
                    .title(R.string.title_delete)
                    .message(R.string.delete_message)
                taskViewModel.apply {
                    taskIdLiveData.value = data.id
                    taskUserIdLiveData.value = data.userId
                    taskTitleLiveData.value = data.title
                    taskNoteLiveData.value = data.note
                    taskBodyLiveData.value = data.body
                    taskStatusLiveData.value = data.status
                }

                positiveButton(R.string.action_delete) {
                    taskViewModel.deleteTask()
                }
                show()
            }
    }
}