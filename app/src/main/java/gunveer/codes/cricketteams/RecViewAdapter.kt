package gunveer.codes.cricketteams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecViewAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RecViewAdapter.RecViewHolder>() {

    private val dataSet: ArrayList<Teams> = ArrayList()

    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        val textView: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener {
                itemView.setOnClickListener(this)
            }
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rec_view_layout, parent, false)
        return RecViewHolder(view)
    }


    override fun getItemCount(): Int {
        if(dataSet != null){
            return dataSet.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.textView.text = dataSet[position].teamName
    }

    fun updateTeams(updatedTeams: ArrayList<Teams>){
        dataSet.clear()
        dataSet.addAll(updatedTeams)
        notifyDataSetChanged()
    }

}
