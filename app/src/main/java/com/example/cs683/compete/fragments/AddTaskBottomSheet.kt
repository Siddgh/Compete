package com.example.cs683.compete.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.cs683.compete.R
import com.example.cs683.compete.databinding.BottomSheetAddTasksBinding
import com.example.cs683.compete.models.Task
import com.example.cs683.compete.utils.FireStoreUtils
import com.example.cs683.compete.utils.StringUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskBottomSheet() : BottomSheetDialogFragment() {

    private lateinit var _binding: BottomSheetAddTasksBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddTasksBinding.inflate(inflater)
        var view = inflater.inflate(R.layout.bottom_sheet_add_tasks, container, false)
        view = _binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBottomSheet()

        _binding.fabBottomSheetAddTask.setOnClickListener {
            val task = Task(
                _binding.etBottomSheetTask.text.toString(),
                StringUtils.getTaskTypeIdFromTaskTypeName(_binding.spnBottomSheetTaskType.selectedItem.toString()),
                StringUtils.getTaskMetricIdFromTaskMetricName(_binding.spnBottomSheetTaskMetric.selectedItem.toString())
            )
            FireStoreUtils.addTaskToFireStore(task)
            dismiss()
        }

    }

    private fun setUpBottomSheet() {
        val taskType = resources.getStringArray(R.array.task_type)
        val taskMetric = resources.getStringArray(R.array.task_metric)

        _binding.spnBottomSheetTaskType.adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, taskType) }

        _binding.spnBottomSheetTaskMetric.adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, taskMetric) }
    }

}