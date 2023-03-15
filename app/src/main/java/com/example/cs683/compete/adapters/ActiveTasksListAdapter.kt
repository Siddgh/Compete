package com.example.cs683.compete.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs683.compete.R
import com.example.cs683.compete.models.Task
import com.example.cs683.compete.utils.NavigationUtils
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ActiveTasksListAdapter(
    options: FirestoreRecyclerOptions<Task>,
) : FirestoreRecyclerAdapter<Task, ActiveTasksListAdapter.ViewHolder>(options) {
    class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Task) {
        holder.itemView.apply {
            val taskTitle = findViewById<TextView>(R.id.tv_recycler_item_task_title)
            val taskCard = findViewById<CardView>(R.id.cv_recycler_item_main)

            val withTag = findViewById<TextView>(R.id.tv_recycler_item_with_title)
            val cardViewProfile1 = findViewById<CardView>(R.id.cv_recycler_item_profile1)
            val cardViewProfile2 = findViewById<CardView>(R.id.cv_recycler_item_profile2)
            val cardViewProfile3 = findViewById<CardView>(R.id.cv_recycler_item_profile3)

            taskTitle.text = model.taskTitle
            taskCard.setOnClickListener {
                NavigationUtils.goToTaskList(holder.itemView.context, model)
            }

            if (model.taskType == 0){
                withTag.visibility = TextView.GONE
                cardViewProfile1.visibility = CardView.GONE
                cardViewProfile2.visibility = CardView.GONE
                cardViewProfile3.visibility = CardView.GONE
            }else{

            }

        }
    }
}