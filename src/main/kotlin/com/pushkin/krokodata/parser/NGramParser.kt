package com.pushkin.krokodata.parser

import java.io.File

data class NGram(
    val freq: Int,
    val words: List<String>,
    val lineNumber: Long
)

class NGramParser(private val filePath: String) : AbstractStringParser() {

    companion object {
        private val DELIMITER = "\\s+".toRegex()
        private const val MIN_WORD_SIZE = 5

        private val badSuffixes = listOf("их", "ых", "ах", "ую", "ии", "ей", "ой", "ом", "ою")
    }

    val data: List<NGram> = parse()

    private fun parse(): List<NGram> {
        val startedAt = System.currentTimeMillis()
        val lines = File(filePath)
            .inputStream()
            .bufferedReader()
            .lines()
        var lineNumber = 0L
        val nGrams = mutableListOf<NGram>()
        lines.iterator().forEach {
            val nGram = lineToNGram(it, lineNumber, true)
            if (nGram != null) {
                nGrams.add(nGram)
            }
            lineNumber++
        }
        nGrams.sortBy(NGram::freq)
//        nGrams.shuffle(Random(System.currentTimeMillis()))
        val finishedAt = System.currentTimeMillis()
        System.err.println("Parsed ${nGrams.size} n-grams in ${((finishedAt - startedAt) / 1000).toInt()} seconds")
        return nGrams
    }

    private fun lineToNGram(row: String, lineNumber: Long, stripEnding: Boolean): NGram? {
        val split = row.split(DELIMITER)
        if (split.size < 2) {
            throw IllegalArgumentException("Invalid row: $row")
        }
        val words = split.subList(1, split.size)
        if (!wordsAreLegit(words)) {
            return null
        }
        return NGram(
            split[0].toInt(),
            words.map { if (stripEnding) stripEnding(it, 4) else it },
            lineNumber
        )
    }

    fun wordsAreLegit(words: List<String>): Boolean =
        words.none { it.length < MIN_WORD_SIZE } &&
                words.all { isSuffixLegit(it) }

    private fun isSuffixLegit(word: String): Boolean =
        badSuffixes.none(word::endsWith)

    fun findOriginalNGram(nGram: NGram): NGram =
        File(filePath)
            .inputStream()
            .bufferedReader()
            .lines()
            .skip(nGram.lineNumber)
            .map { lineToNGram(it, nGram.lineNumber, false) ?: throw IllegalArgumentException("Bad line: $it") }
            .findFirst()
            .orElseThrow { IllegalArgumentException("NGram not found") }

}
