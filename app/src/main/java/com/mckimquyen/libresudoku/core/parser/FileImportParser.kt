package com.mckimquyen.libresudoku.core.parser

interface FileImportParser {
    fun toBoards(content: String): Pair<Boolean, List<String>>
}
