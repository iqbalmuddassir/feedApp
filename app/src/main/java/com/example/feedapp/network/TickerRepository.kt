package com.example.feedapp.network

import android.content.Context
import com.example.feedapp.model.Ticker
import okhttp3.ResponseBody
import retrofit2.Retrofit
import java.io.*

class TickerRepository(private val context: Context) {

    private val rawTickers = mutableMapOf<String, List<Ticker>>()
    private val currentTickers = mutableListOf<Ticker>()

    private fun fetchTickers() {
        val service = tickerApiService()
        val response = service.downloadTickerCsvSync(TICKER_CSV_URL).execute()
        response.body()?.let {
            writeResponseBodyToDisk(it)
            readCsv()
        }
    }

    private fun tickerApiService(): TickerApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(TickerApiService::class.java)
    }

    fun refreshTicker(): List<Ticker> {
        if (rawTickers.isEmpty()) {
            fetchTickers()
        }

        currentTickers.clear()
        currentTickers.addAll(rawTickers.values
            .map {
                val randomIndexGenerator = randomIndexGenerator(it.size)
                it[randomIndexGenerator]
            })
        return currentTickers
    }

    private fun writeResponseBodyToDisk(body: ResponseBody): Boolean {
        return try {
            val outputFile = File(getFilePathForCsv())
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(outputFile)
                while (true) {
                    val read: Int = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }

    private fun getFilePathForCsv() = context.getExternalFilesDir(null)?.path + File.separator.toString() + CSV_FILE_NAME

    private fun readCsv(): Any {
        val file = File(getFilePathForCsv())
        val inputStream: InputStream = file.inputStream()
        val reader = inputStream.bufferedReader()

        rawTickers.clear()

        val list: List<Ticker> = reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (name, value) = it.split(',', ignoreCase = false, limit = 3)
                val price = value.toDoubleOrNull() ?: return@map Ticker.EMPTY
                Ticker(name.replace("\"", ""), price)
            }.filter { Ticker.EMPTY != it }.toList()

        rawTickers.putAll(list.groupBy { it.symbol })
        return list
    }

    private fun randomIndexGenerator(size: Int) = (0..size).random() % size

    companion object {
        const val TICKER_CSV_URL = "https://raw.githubusercontent.com/dsancov/TestData/main/stocks.csv"
        const val BASE_URL = "https://saurav.tech/"
        const val CSV_FILE_NAME = "tickerData.csv"
    }
}