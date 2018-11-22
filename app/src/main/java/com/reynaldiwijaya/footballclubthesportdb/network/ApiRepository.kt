package com.reynaldiwijaya.footballclubthesportdb.network

import java.net.URL

class ApiRepository {

    //TODO Membuat Fun doRequest yang didalamnya terdapat Extensions Functions readText untuk memmbaca url
    fun doRequest(url: String): String{
        return URL(url).readText()
    }
}