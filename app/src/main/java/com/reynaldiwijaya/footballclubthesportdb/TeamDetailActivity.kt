package com.reynaldiwijaya.footballclubthesportdb

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.reynaldiwijaya.footballclubthesportdb.R.color.colorAccent
import com.reynaldiwijaya.footballclubthesportdb.R.color.colorPrimary
import com.reynaldiwijaya.footballclubthesportdb.R.drawable.*
import com.reynaldiwijaya.footballclubthesportdb.R.id.add_to_favorite
import com.reynaldiwijaya.footballclubthesportdb.R.menu.detail_menu
import com.reynaldiwijaya.footballclubthesportdb.database.database
import com.reynaldiwijaya.footballclubthesportdb.main.TeamDetailPresenter
import com.reynaldiwijaya.footballclubthesportdb.main.TeamDetailView
import com.reynaldiwijaya.footballclubthesportdb.model.Favorite
import com.reynaldiwijaya.footballclubthesportdb.model.Team
import com.reynaldiwijaya.footballclubthesportdb.network.ApiRepository
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<Team>) {
        //display data on UI
        teams = Team(data[0].teamId,
                data[0].teamName,
                data[0].teamBadge)
        swipeRefresh.isRefreshing = false
        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
        teamDescription.text = data[0].teamDescription
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite-> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //TODO Fungsi untuk menambahkan data ke database favorite
    private fun addToFavorite(){
        try {
            database.use{
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.TEAM_ID to teams.teamId,
                        Favorite.TEAM_NAME to teams.teamName,
                        Favorite.TEAM_BADGE to teams.teamBadge)
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    //TODO untuk menghapus data dari database favorite
    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(TEAM_ID = {id})",
                        "id" to id)
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    //TODO Fungsi untuk untuk mengatur pada tombol icon, ketika tim yang dimaksud belum ditambahkan icon nya akan menjadi warna apa, sedangkan tim yang dimaksud sudah ditambahkan icon akan berubah menjadi warna lain
    private fun setFavorite(){
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    //TODO Fungsi untuk mengecek apakah sebuah tim sudah dimasukan kedalam database atau belum
    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(TEAM_ID = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private lateinit var id: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent, android.R.color.holo_green_light, android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            teamBadge = imageView {  }.lparams(height = dip(75))
                            teamName = textView {
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context,colorAccent)
                            }.lparams {
                                topMargin = dip(5)
                            }
                            teamFormedYear = textView {
                                this.gravity = Gravity.CENTER
                            }
                            teamStadium = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, colorPrimary)
                            }
                            teamDescription = textView().lparams {
                                topMargin = dip(20)
                            }
                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        val intent = intent
        id = intent.getStringExtra("id")

        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)
        swipeRefresh.onRefresh {
            presenter.getTeamDetail(id)
        }
    }
}