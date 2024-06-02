package com.mckimquyen.libresudoku.data.db.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.mckimquyen.libresudoku.core.qqwing.GameDifficulty
import com.mckimquyen.libresudoku.core.qqwing.GameType
import java.time.Duration
import java.time.ZonedDateTime

@Keep
@Entity(
    tableName = "record",
    foreignKeys = [ForeignKey(
        onDelete = CASCADE,
        entity = SudokuBoard::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("board_uid")
    )]
)
data class Record(
    @PrimaryKey @ColumnInfo(name = "board_uid") val boardUid: Long,
    @ColumnInfo(name = "type") val type: GameType,
    @ColumnInfo(name = "difficulty") val difficulty: GameDifficulty,
    @ColumnInfo(name = "date") val date: ZonedDateTime,
    @ColumnInfo(name = "time") val time: Duration,
)
