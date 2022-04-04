package com.pushkin.krokodata.matcher

import com.pushkin.krokodata.parser.NGramParser
import com.pushkin.krokodata.parser.NumFileParser
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import kotlin.test.assertTrue

class WordMatcherTest {

    private lateinit var wordMatcher: WordMatcher

    @BeforeClass
    fun setUp() {
        wordMatcher = WordMatcher(
            NumFileParser("testData/adj.tst")
                .also {
                    println("Adjectives:\n${it.data.joinToString("\n")}")
                },
            NumFileParser("testData/noun.tst")
                .also {
                    println("Nouns:\n${it.data.joinToString("\n")}")
                },
            NGramParser("testData/2grams.tst")
                .also {
                    println("NGrams:\n${it.data.joinToString("\n")}")
                }
        )
    }

    @Test
    fun testRandomMatch() {
        repeat(10) {
            val match = wordMatcher.randomMatch(1000)
            println(match)
            assertTrue(match.words.isNotEmpty())
        }
    }

    @Test
    fun testRandomWordPair() {
        repeat(100) {
            with(wordMatcher.randomWordPair()) {
                assertTrue(first.isNotEmpty())
                assertTrue(second.isNotEmpty())
                println(this)
            }
        }
    }
}
