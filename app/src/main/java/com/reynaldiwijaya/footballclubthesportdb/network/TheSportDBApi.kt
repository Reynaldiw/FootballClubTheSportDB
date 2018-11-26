package com.reynaldiwijaya.footballclubthesportdb.network

import android.net.Uri
import com.reynaldiwijaya.footballclubthesportdb.BuildConfig

object TheSportDBApi {

    //TODO Membuat fungsi untuk menampung endpoint yang akan digunakan
    //Kita menggunakan endpoint /search_all_teams.php?id=+LEAGUE_NAME
//    fun getTeams(league: String?): String{
//        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
//    }

    //Ini Kodingan yang lebih simple
    fun getTeams (league: String?): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("search_all_teams.php")
                .appendQueryParameter("l", league)
                .build()
                .toString()
    }

    fun getTeamDetail(teamId: String?): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupteam.php")
                .appendQueryParameter("id", teamId)
                .build()
                .toString()

    }
}