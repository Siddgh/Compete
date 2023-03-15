package com.example.cs683.compete.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cs683.compete.R
import com.example.cs683.compete.adapters.ActiveTasksListAdapter
import com.example.cs683.compete.fragments.AddTaskBottomSheet
import com.example.cs683.compete.databinding.ActivityMainBinding
import com.example.cs683.compete.models.Task
import com.example.cs683.compete.utils.DesignUtils
import com.example.cs683.compete.utils.FireStoreUtils
import com.example.cs683.compete.utils.FirebaseAuthUtils
import com.example.cs683.compete.utils.NavigationUtils
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.navigation.NavigationView

class Home : AppCompatActivity() {

    private lateinit var tasksListAdapter: ActiveTasksListAdapter
    private lateinit var _binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DesignUtils.blendStatusBar(this, window)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)


        _binding.fabHomeAddTask.setOnClickListener {
            val bottomSheetFragment = AddTaskBottomSheet()
            NavigationUtils.showAddTaskBottomSheet(supportFragmentManager, bottomSheetFragment)
        }

        setUpToolBar(_binding)
        setUpNavHeader(_binding.mainNavigationMenu)
        setUpTasksRecyclerView()
    }

    private fun setUpNavHeader(mainNavigationMenu: NavigationView) {
        val headerView = mainNavigationMenu.getHeaderView(0)
        FirebaseAuthUtils.setUserProfile(
            headerView!!.findViewById(R.id.tv_nav_header_profile_name),
            headerView.findViewById(R.id.iv_nav_header_profile_pic),
            baseContext
        )
    }

    private fun setUpToolBar(_binding: ActivityMainBinding) {

        _binding.mainToolbar.title = ""
        setSupportActionBar(_binding.mainToolbar)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            _binding.mainDrawerLayout,
            _binding.mainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        _binding.mainDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    private fun setUpTasksRecyclerView() {
        _binding.includeRecyclerviewWithHeaderTasks.tvRecyclerviewWithHeaderTitle.text =
            getString(R.string.header_active_tasks)
        val options: FirestoreRecyclerOptions<Task> =
            FirestoreRecyclerOptions.Builder<Task>().setQuery(
                FireStoreUtils.getTasksQuery,
                Task::class.java
            ).setLifecycleOwner(this).build()

        tasksListAdapter = ActiveTasksListAdapter(options = options)

        _binding.includeRecyclerviewWithHeaderTasks.recyclerviewWithHeader.apply {
            isNestedScrollingEnabled = true
            adapter = tasksListAdapter
            layoutManager = GridLayoutManager(context, 2)
            itemAnimator = null
        }

    }

}