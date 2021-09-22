package br.com.part.codelabs.feature.list.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.part.codelabs.feature.data.entity.TaskDto
import java.util.*
import br.com.part.codelabs.R
import br.com.part.codelabs.components.StatusView
import br.com.part.codelabs.extensions.convertToTaskDateDto
import br.com.part.codelabs.feature.data.entity.Status


class TasklistAdapter internal constructor(
    context: Context,
    private val listener: (Long) -> Unit
): RecyclerView.Adapter<TasklistAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var tasks = emptyList<TaskDto>()

    inner class TaskViewHolder(
        itemView: View,
        private val listener: (Long) -> Unit
    ): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val taskItemNameView: TextView = itemView.findViewById(R.id.txtItemTaskName)
        private val taskItemStatusView: StatusView = itemView.findViewById(R.id.txtItemTaskStatus)
        private val taskItemDateView:TextView = itemView.findViewById(R.id.txtItemTaskDate)

        private lateinit var taskDto: TaskDto

        fun bind(data: TaskDto){
            taskDto = data
            taskItemNameView.text = data.name
            taskItemStatusView.text = data.state
            taskItemStatusView.setStatus(Status.setValueOf(data.state))
            taskItemDateView.text = (data.date?.convertToTaskDateDto()?.getDateTimeSt() ?: "").toString()

            itemView.setOnClickListener{
                listener.invoke(taskDto.id)
            }
        }

        override fun onClick(v: View?) {
            listener.invoke(taskDto.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    internal fun submit(tasks: List<TaskDto>){
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tasks.size


}
