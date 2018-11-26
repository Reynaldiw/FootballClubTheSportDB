package com.reynaldiwijaya.footballclubthesportdb.main

import com.google.gson.Gson
import com.reynaldiwijaya.footballclubthesportdb.network.ApiRepository
import com.reynaldiwijaya.footballclubthesportdb.model.TeamResponse
import com.reynaldiwijaya.footballclubthesportdb.network.TheSportDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter (private val view: TeamsView, private val apiRepository: ApiRepository, private val gson: Gson) {
    fun getTeamList(league: String?){
        //Behaviour ketika presenter sedang menunggu respon, progressbar akan ditampilkan
        view.showLoading()
        doAsync {
            //Fungsi doAsync dari anko bisa digunakan untuk menjalankan asynchronous task. Ia akan memanggil funsi doRequest
            //dan jika berhasil data akan ditampilkan oleh uiThread
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams(league)),
                    TeamResponse::class.java
            )

            //Behaviour ketika respon berhasil di dapatkan , progressBar akan hilang dan data akan di tampilkan di dalam view
            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}