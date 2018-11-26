package com.reynaldiwijaya.footballclubthesportdb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.reynaldiwijaya.footballclubthesportdb.R.id.*
import com.reynaldiwijaya.footballclubthesportdb.fragments.FavoriteTeamsFragment
import com.reynaldiwijaya.footballclubthesportdb.fragments.TeamsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //TODO Membuat Listener dan didalam listener tersebut kita tambahkan when untuk berpindah fragment
        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when (item.itemId){
                teams -> {
                    loadTeamsFragment(savedInstanceState)

                }
                favorites -> {
                    loadFavoritesFragment(savedInstanceState)

                }
            }
            true
        }
        bottom_navigation.selectedItemId = teams
    }
    //TODO Membuat method untuk menampilkan TeamsFragment
    private fun loadTeamsFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamsFragment(), TeamsFragment::class.java.simpleName)
                    .commit()
        }
    }

    //TODO Membuat method untuk menampilkan FavoritesFragment
    private fun loadFavoritesFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteTeamsFragment(), FavoriteTeamsFragment::class.java.simpleName)
                    .commit()
        }
    }
}
