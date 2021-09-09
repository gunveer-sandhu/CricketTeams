package gunveer.codes.cricketteams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import gunveer.codes.cricketteams.databinding.ActivityTeamViewBinding
import java.text.FieldPosition

class TeamView : AppCompatActivity() {

    private lateinit var binding: ActivityTeamViewBinding
    private var teams: ArrayList<Teams> = ArrayList()
    private lateinit var mAdapter: RecViewTeamViewAdapter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        position = intent.extras?.get("position") as Int
        teams = intent.extras?.get("teams") as ArrayList<Teams>
        title = "Team " + teams[position].teamName

        binding.recViewTeamView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@TeamView)
            mAdapter = RecViewTeamViewAdapter(teams[position].players)
            adapter = mAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_team_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.sortFirstName -> {
                sortFirstName()
                true
            }
            R.id.sortLastName -> {
                sortLastName()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun sortLastName() {
        teams[position].players.sortWith(compareBy { it.lastName })
        mAdapter.notifyDataSetChanged()
    }

    private fun sortFirstName() {
        teams[position].players.sortWith(compareBy { it.firstName })
        mAdapter.notifyDataSetChanged()
    }
}