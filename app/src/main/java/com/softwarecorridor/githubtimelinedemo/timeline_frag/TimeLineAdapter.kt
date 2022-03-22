package com.softwarecorridor.githubtimelinedemo.timeline_frag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.softwarecorridor.githubtimelinedemo.R


//https://developer.android.com/guide/topics/ui/layout/recyclerview
class TimeLineAdapter(private val repoList : ArrayList<RepoModel>) : RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder>(){
//    private lateinit var mLayoutInflater: LayoutInflater

    class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        val name : TextView
        val description : TextView
        val timeline : TimelineView

        init {
            name = itemView.findViewById(R.id.item_name)
            description = itemView.findViewById(R.id.item_description)
            timeline = itemView.findViewById(R.id.item_timeline)

            timeline.initLine(viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TimeLineViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.timeline_item, viewGroup, false)

        return TimeLineViewHolder(view, viewType)

//        if(!::mLayoutInflater.isInitialized) {
//            mLayoutInflater = LayoutInflater.from(parent.context)
//        }
//
//        return TimeLineViewHolder(mLayoutInflater.inflate(R.layout.timeline_item, parent, false), viewType)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        val model = repoList[position]

        holder.name.text = model.name
        holder.description.text = model.description
    }

//    private fun setMarker(holder: TimeLineViewHolder, drawableResId: Int, colorFilter: Int) {
//        holder.timeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, drawableResId, ContextCompat.getColor(holder.itemView.context, colorFilter))
//    }

    override fun getItemCount() = repoList.size


}