package com.reynaldiwijaya.footballclubthesportdb.main

import com.reynaldiwijaya.footballclubthesportdb.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}