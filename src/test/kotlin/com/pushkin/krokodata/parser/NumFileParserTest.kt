package com.pushkin.krokodata.parser

import org.testng.Assert.assertTrue
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class NumFileParserTest {

    private lateinit var parser: NumFileParser

    @BeforeMethod
    fun setUp() {
        parser = NumFileParser("testData/adj.tst")
    }

    @Test
    fun parse() {
        assertTrue(parser.data.isNotEmpty())
    }

}
