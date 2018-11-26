package com.reynaldiwijaya.footballclubthesportdb.main

import com.reynaldiwijaya.footballclubthesportdb.model.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}