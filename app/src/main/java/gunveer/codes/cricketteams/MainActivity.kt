package gunveer.codes.cricketteams

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import gunveer.codes.cricketteams.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity(), RecViewAdapter.OnItemClickListener {

    companion object {
        private const val TAG = "list"
    }

    private var teams: ArrayList<Teams> = ArrayList()
    private var players: ArrayList<Players> = ArrayList()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: RecViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkCall()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            mAdapter = RecViewAdapter(this@MainActivity)
            adapter = mAdapter
        }
    }


    private fun networkCall() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://test.oye.direct/players.json"
        val jsonObjectRequest = JsonObjectRequest(url, Response.Listener { response ->
            response.keys().forEach {
                players = ArrayList()
                for (i in 0 until response.getJSONArray(it).length()){
                    players.add(Players(
                        //setting first name
                        response.getJSONArray(it).getJSONObject(i).get("name").toString().split(" ").first(),
                        //setting second name if there is one else setting last name as empty
                        if(response.getJSONArray(it).getJSONObject(i).get("name").toString().split(" ").size == 1 ||
                            response.getJSONArray(it).getJSONObject(i).get("name").toString().split(" ").size >=3)
                                //second name is there or no last name is there,
                            if(response.getJSONArray(it).getJSONObject(i).get("name").toString().split(" ").size == 1) "" // no last name
                                else response.getJSONArray(it).getJSONObject(i).get("name").toString().split(" ").elementAt(1) + " "
                                    + response.getJSONArray(it).getJSONObject(i).get("name").toString().split(" ").last() // last name as well as second name
                        else response.getJSONArray(it).getJSONObject(i).get("name").toString().split(" ").last(), // only last name
                        //setting captaincy
                        try{
                            response.getJSONArray(it).getJSONObject(i).get("captain") as Boolean
                        }catch( e: Exception){
                            false
                        }
                    ))
                }
                teams.add(Teams(it, players))
            }
            mAdapter.updateTeams(teams)
            binding.progressBar.visibility = View.GONE
            Snackbar.make(binding.root, "Double click on any team name.", Snackbar.LENGTH_LONG).show()
        }, Response.ErrorListener {
            Log.d(TAG, "networkCall: $it")
            Snackbar.make(binding.root, "Some error has occurred. $it", Snackbar.LENGTH_LONG).show()
        })
        queue.add(jsonObjectRequest)
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, TeamView::class.java)
        intent.putExtra("position", position)
        intent.putExtra("teams", teams)
        startActivity(intent)
    }
}