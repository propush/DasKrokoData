package com.pushkin.krokodata.parser

abstract class AbstractStringParser {

    fun stripEnding(str: String, minLength: Int): String =
        if (str.length > minLength) str.substring(0, str.length - 1) else str

}
