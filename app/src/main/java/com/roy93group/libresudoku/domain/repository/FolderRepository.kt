package com.roy93group.libresudoku.domain.repository

import com.roy93group.libresudoku.data.db.model.Folder
import com.roy93group.libresudoku.data.db.model.SavedGame
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    fun getAll(): Flow<List<Folder>>
    fun get(uid: Long): Flow<Folder>
    fun countPuzzlesFolder(uid: Long): Long
    fun getLastSavedGamesAnyFolder(gamesCount: Int): Flow<List<SavedGame>>
    suspend fun insert(folder: Folder): Long
    suspend fun update(folder: Folder)
    suspend fun delete(folder: Folder)
}
