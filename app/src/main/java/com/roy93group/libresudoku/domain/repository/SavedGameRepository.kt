package com.roy93group.libresudoku.domain.repository

import com.roy93group.libresudoku.data.database.model.SavedGame
import com.roy93group.libresudoku.data.database.model.SudokuBoard
import kotlinx.coroutines.flow.Flow

interface SavedGameRepository {
    fun getAll(): Flow<List<SavedGame>>
    suspend fun get(uid: Long): SavedGame?
    fun getWithBoards(): Flow<Map<SavedGame, SudokuBoard>>
    fun getLast(): Flow<SavedGame?>
    suspend fun insert(savedGame: SavedGame): Long
    suspend fun update(savedGame: SavedGame)
    suspend fun delete(savedGame: SavedGame)
}
