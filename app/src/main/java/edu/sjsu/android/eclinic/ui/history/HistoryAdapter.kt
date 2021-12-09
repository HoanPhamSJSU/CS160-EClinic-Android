package edu.sjsu.android.eclinic.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.sjsu.android.eclinic.databinding.ItemHistoryBinding
import edu.sjsu.android.eclinic.models.History
import java.text.SimpleDateFormat
import java.util.*


class HistoryAdapter : ListAdapter<History, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val historyList: ArrayList<History> = arrayListOf()

    var itemClick: (History) -> Unit = {}


    fun addHistory(newDoctors: List<History>) {
        historyList.addAll(newDoctors)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val textName: TextView = binding.textTitle
        val textHistoryDate: TextView = binding.textDate

        init {
            binding.root.setOnClickListener {
                itemClick.invoke(historyList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        val history: History = historyList[position]
        holder.textName.text = history.title
        holder.textHistoryDate.text =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(history.date)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<History> =
            object : DiffUtil.ItemCallback<History>() {
                override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                    return oldItem.documentId == newItem.documentId
                }

                override fun areContentsTheSame(
                    oldItem: History, newItem: History,
                ): Boolean {
                    return oldItem.title == newItem.title
                }
            }
    }
}