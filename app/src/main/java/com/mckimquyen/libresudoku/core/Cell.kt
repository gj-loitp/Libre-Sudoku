package com.mckimquyen.libresudoku.core

import androidx.annotation.Keep

@Keep
data class Cell(
    val row: Int,
    val col: Int,
    var value: Int = 0,
    var error: Boolean = false,
    var locked: Boolean = false,
)
