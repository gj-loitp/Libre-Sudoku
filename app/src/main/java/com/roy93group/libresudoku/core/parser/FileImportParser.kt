package com.roy93group.libresudoku.core.parser

interface FileImportParser {
    fun toBoards(content: String): Pair<Boolean, List<String>>
}
