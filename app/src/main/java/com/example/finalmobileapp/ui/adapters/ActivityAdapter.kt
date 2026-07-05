package com.example.finalmobileapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalmobileapp.data.entity.ActivityEntity
import com.example.finalmobileapp.databinding.ItemActivityBinding

class ActivityAdapter(
    private var activities: List<ActivityEntity>,
    private val onBookClick: (ActivityEntity) -> Unit
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    class ActivityViewHolder(val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activities[position]
        holder.binding.apply {
            tvActivityName.text = activity.name
            tvActivityDescription.text = activity.description
            tvActivityPrice.text = "$${activity.price} / person"
            ivActivity.setImageResource(activity.imageResId)
            btnBookActivity.setOnClickListener { onBookClick(activity) }
        }
    }

    override fun getItemCount() = activities.size

    fun updateData(newActivities: List<ActivityEntity>) {
        activities = newActivities
        notifyDataSetChanged()
    }
}
