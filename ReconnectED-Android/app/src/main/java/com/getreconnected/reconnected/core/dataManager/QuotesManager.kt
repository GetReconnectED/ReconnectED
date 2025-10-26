package com.getreconnected.reconnected.core.dataManager

import android.content.Context
import com.getreconnected.reconnected.core.FilePaths
import com.getreconnected.reconnected.core.models.entities.Quote
import com.google.gson.Gson

object QuotesManager {
    private var quotes = emptyArray<Quote>()

    fun getQuotes(context: Context): Array<Quote> {
        if (quotes.isEmpty()) {
            load(context)
        }

        return quotes
    }

    fun load(context: Context) {
        val inputStream = context.assets.open(FilePaths.QUOTES)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, charset = Charsets.UTF_8)
        val gson = Gson()
        quotes = gson.fromJson(json, Array<Quote>::class.java)
    }
}
