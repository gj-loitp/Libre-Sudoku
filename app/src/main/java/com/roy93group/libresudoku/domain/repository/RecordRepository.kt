package com.roy93group.libresudoku.domain.repository

import com.roy93group.libresudoku.core.qqwing.GameDifficulty
import com.roy93group.libresudoku.core.qqwing.GameType
import com.roy93group.libresudoku.data.database.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    suspend fun get(uid: Long): Record
    fun getAll(): Flow<List<Record>>
    fun getAllSortByTime(): Flow<List<Record>>
    fun getAll(difficulty: GameDifficulty, type: GameType): Flow<List<Record>>
    suspend fun insert(record: Record)
    suspend fun delete(record: Record)
}