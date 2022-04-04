package com.pushkin.krokodata.parser

import org.testng.Assert.*
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test


class NGramParserTest {

    private lateinit var parser: NGramParser

    @BeforeClass
    fun setUp() {
        parser = NGramParser("testData/2grams.tst")
    }

    @Test
    fun parser() {
        assertTrue(parser.data.isNotEmpty())
    }

    @Test
    fun findOriginalNGram() {
        assertEquals(
            listOf("лёгкое", "слово"),
            parser.findOriginalNGram(NGram(3, listOf("лёгко", "слово"), 3)).words
        )
    }

    @Test
    fun wordsAreLegit() {
        assertTrue(parser.wordsAreLegit(listOf("красивый", "памятник")))
        assertFalse(parser.wordsAreLegit(listOf("лоб", "котелок")))
    }
}
