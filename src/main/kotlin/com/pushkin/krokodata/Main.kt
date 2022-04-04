package com.pushkin.krokodata

import com.pushkin.krokodata.matcher.WordMatcher
import com.pushkin.krokodata.parser.NGramParser
import com.pushkin.krokodata.parser.NumFileParser

fun main(args: Array<String>) {
    System.err.println("Word matcher for krokoData project.")
    if (args.size != 4) {
        System.err.println("Usage: java -jar krokodata.jar <n-gram count> <adjective file> <noun file> <n-gram file>")
        return
    }
    val nGramCount = args[0].toInt()
    val adjFile = args[1]
    val nounFile = args[2]
    val ngramFile = args[3]
    generateMatchList(nGramCount, adjFile, nounFile, ngramFile)
}

fun generateMatchList(nGramCount: Int, adjFile: String, nounFile: String, ngramFile: String) {
    System.err.println("Initializing parsers...")
    val adjParser = NumFileParser(adjFile)
    val nounParser = NumFileParser(nounFile)
    val ngramParser = NGramParser(ngramFile)
    val matcher = WordMatcher(adjParser, nounParser, ngramParser)
    val krokoData = mutableSetOf<Pair<Int, String>>()
    System.err.println("Generating n-gram list of total $nGramCount n-grams...")
    val startedAt = System.currentTimeMillis()
    repeat(nGramCount) { count ->
        try {
            val match = matcher.randomMatch(1000)
            val originalNGram = ngramParser.findOriginalNGram(match)
            krokoData.add(Pair(originalNGram.freq, originalNGram.words.joinToString(" ")))
        } catch (e: Exception) {
            System.err.println("Error: ${e.message}")
        }
        if (count > 0 && count % 10 == 0) {
            System.err.println("Found: $count")
        }
    }
    val finishedAt = System.currentTimeMillis()
    println(
        krokoData
            .sortedBy { it.first }
            .joinToString("\n") {
                "${it.first} ${it.second}"
            }
    )
    System.err.println("Time generating: ${((finishedAt - startedAt) / 1000).toInt()} seconds")
}
