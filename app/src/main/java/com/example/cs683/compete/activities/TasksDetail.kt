package com.example.cs683.compete.activities

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.cs683.compete.R
import com.example.cs683.compete.constants.GenericConstants
import com.example.cs683.compete.databinding.ActivityTasksDetailBinding
import com.example.cs683.compete.models.Task
import com.example.cs683.compete.utils.*
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import java.util.*

//20201031
//
//20201101
//20201100
//20201099

class TasksDetail : AppCompatActivity() {
    private lateinit var _binding: ActivityTasksDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTasksDetailBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
        DesignUtils.blendStatusBar(this, window)

        val task: Task = NavigationUtils.parseTaskDataFromIntent(
            taskTitle = intent.getStringExtra(GenericConstants.TASK_TITLE).toString(),
            taskType = intent.getIntExtra(GenericConstants.TASK_TYPE, 0),
            taskMetric = intent.getIntExtra(GenericConstants.TASK_METRIC, 0),
            taskId = intent.getStringExtra(GenericConstants.TASK_ID).toString()
        )

        setUpInitialValues(task)

        _binding.chipTasksDetailWeekly.setOnClickListener {
            setUpGraphs("Weekly", task)
        }

        _binding.chipTasksDetailMonthly.setOnClickListener {
            setUpGraphs("Monthly", task)
        }


    }

    private fun setUpGraphs(type: String, task: Task) {
        if (type == "Weekly") {
            RealtimeDatabaseUtils.getLogEntriesFromCurrentUser(7, task.taskId.toString()) {
                val chartModel = AAChartModel().chartType(AAChartType.Area)
                    .dataLabelsEnabled(true)
                    .series(
                        arrayOf(
                            AASeriesElement().name(FirebaseAuthUtils.getUserMeta().displayName)
                                .data(it.toSortedMap().values.toTypedArray()),
                        )
                    )

                _binding.chartTasksDetails.aa_drawChartWithChartModel(chartModel)
            }
        } else if (type == "Monthly") {
            RealtimeDatabaseUtils.getLogEntriesFromCurrentUser(31, task.taskId.toString()) {
                val chartModel = AAChartModel().chartType(AAChartType.Area)
                    .dataLabelsEnabled(true)
                    .series(
                        arrayOf(
                            AASeriesElement().name(FirebaseAuthUtils.getUserMeta().displayName)
                                .data(it.toSortedMap().values.toTypedArray()),
                        )
                    )

                _binding.chartTasksDetails.aa_drawChartWithChartModel(chartModel)
            }
        }
    }

    private fun setUpInitialValues(task: Task) {
        _binding.tvTasksDetailTitle.text = task.taskTitle
        _binding.includeRecyclerviewWithHeaderTasksDetail.tvRecyclerviewWithHeaderTitle.text =
            GenericConstants.FRIENDS_TITLE
        if (task.taskType == 0) {
            _binding.includeRecyclerviewWithHeaderTasksDetail.root.visibility = LinearLayout.GONE
            _binding.includeMetricNumberLayout.btnIncludeMetricNumberDate.text =
                DateUtils.getTodaysDate()

            _binding.includeMetricNumberLayout.btnIncludeMetricNumberDate.setOnClickListener {
                val dialog = ChooseDateFragment(_binding)
                dialog.show(supportFragmentManager, GenericConstants.DATE_PICKER)
            }

            _binding.includeMetricNumberLayout.fabMetricNumberAdd.setOnClickListener {
                val dateVal =
                    _binding.includeMetricNumberLayout.btnIncludeMetricNumberDate.text.toString()
                val inputValue =
                    _binding.includeMetricNumberLayout.edIncludeMetricNumberInput.text.toString()
                if (inputValue.isNotEmpty()) {
                    FireStoreUtils.addTaskLogToFireStore(
                        value = inputValue,
                        date = DateUtils.getYYYYMMDDFromDateString(dateVal),
                        taskMeta = task
                    )
                } else {
                    DesignUtils.displayToast(baseContext, "Value can't be empty")
                }
            }

            _binding.llTasksDetailsFriendsContainer.visibility = LinearLayout.GONE
            setUpGraphs("Weekly", task)

        } else if (task.taskType == 1) {

        } else if (task.taskType == 2) {

        }
    }


}


class ChooseDateFragment(_binding: ActivityTasksDetailBinding) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    val binding = _binding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        binding.includeMetricNumberLayout.btnIncludeMetricNumberDate.text =
            "${month+1}/${dayOfMonth}/${year}"
    }
}