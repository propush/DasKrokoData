package com.pushkin.krokodata.matcher

import com.pushkin.krokodata.parser.NGram
import com.pushkin.krokodata.parser.NGramParser
import com.pushkin.krokodata.parser.NumFileParser
import kotlin.random.Random

class WordMatcherException(message: String) : Exception(message)

class WordMatcher(
    private val adjParser: NumFileParser,
    private val nounParser: NumFileParser,
    private val nGramParser: NGramParser
) {

    private val random = Random(System.currentTimeMillis())

    fun randomMatch(epochs: Int): NGram {
        for (epoch in 0..epochs) {
            val (adj, noun) = randomWordPair()
            for (nGram in nGramParser.data) {
                if (isMatched(adj, noun, nGram)) {
                    return nGram
                }
            }
        }
        throw WordMatcherException("No match found")
    }

    private fun isMatched(adj: String, noun: String, nGram: NGram): Boolean =
        nGram.words.contains(adj) && nGram.words.contains(noun)

    fun randomWordPair(): Pair<String, String> {
        val adj = adjParser.data.random(random).word
        val noun = nounParser.data.random(random).word
        return Pair(adj, noun)
    }

}
