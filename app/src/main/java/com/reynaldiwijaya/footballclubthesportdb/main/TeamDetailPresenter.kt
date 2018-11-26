package com.reynaldiwijaya.footballclubthesportdb.main

import com.google.gson.Gson
import com.reynaldiwijaya.footballclubthesportdb.model.TeamResponse
import com.reynaldiwijaya.footballclubthesportdb.network.ApiRepository
import com.reynaldiwijaya.footballclubthesportdb.network.TheSportDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

//TODO Untuk menampung fungsi untuk melakukan request data ke server
class TeamDetailPresenter (private val view: TeamDetailView, private val apiRepository: ApiRepository,private val gson: Gson){
    fun getTeamDetail(teamId: String){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(teamId)), TeamResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showTeamDetail(data.teams)
            }
        }
    }
}