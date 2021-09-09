package gunveer.codes.cricketteams

import java.io.Serializable


data class Teams(val teamName: String, val players: ArrayList<Players>) : Serializable

data class Players(val firstName: String, val lastName: String, val isCaptain: Boolean)  : Serializable
