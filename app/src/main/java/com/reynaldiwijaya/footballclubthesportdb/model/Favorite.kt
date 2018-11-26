package com.reynaldiwijaya.footballclubthesportdb.model

data class Favorite (val id: Long?, var teamId: String, var teamName: String?, val teamBadge: String?){
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}