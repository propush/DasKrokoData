package com.pushkin.krokodata.parser

import java.io.File

data class NumRow(
    val freq: Int,
    val word: String
)

class NumFileParser(private val filePath: String) : AbstractStringParser() {

    val data: List<NumRow> = parse()

    private fun parse(): List<NumRow> =
        File(filePath)
            .inputStream()
            .bufferedReader()
            .lines()
            .map(::rowToNumRow)
            .toList()
            .also {
                System.err.println("Parsed ${it.size} rows")
            }

    private fun rowToNumRow(row: String): NumRow {
        val (_, freq, data, _) = row.split("\\s+".toRegex())
        return NumRow(freq.toDouble().toInt(), stripEnding(data, 4))
    }

}
