package gunveer.codes.cricketteams

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecViewTeamViewAdapter(dataSet: ArrayList<Players>):
    RecyclerView.Adapter<RecViewTeamViewAdapter.RecViewHolder>() {
    private val dataSet: ArrayList<Players> = dataSet

    class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.tvPlayerName)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rec_view_team_view_adapter, parent, false)
        return RecViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.textView.text = dataSet[position].firstName + " " + dataSet[position].lastName
        if(!dataSet[position].isCaptain){
            holder.imageView.visibility = View.GONE
            holder.itemView.findViewById<LinearLayout>(R.id.linearLayout).weightSum = 4f
        }
    }

    override fun getItemCount(): Int {
        if(dataSet != null){
            return dataSet.size
        }
        return 0
    }
}