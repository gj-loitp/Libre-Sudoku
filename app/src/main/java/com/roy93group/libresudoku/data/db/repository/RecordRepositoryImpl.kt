package com.roy93group.libresudoku.data.db.repository

import com.roy93group.libresudoku.core.qqwing.GameDifficulty
import com.roy93group.libresudoku.core.qqwing.GameType
import com.roy93group.libresudoku.data.db.dao.RecordDao
import com.roy93group.libresudoku.data.db.model.Record
import com.roy93group.libresudoku.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow

class RecordRepositoryImpl(
    private val recordDao: RecordDao,
) : RecordRepository {
    override suspend fun get(uid: Long): Record = recordDao.get(uid)
    override fun getAll(): Flow<List<Record>> = recordDao.getAll()
    override fun getAllSortByTime(): Flow<List<Record>> = recordDao.getAllSortByTime()
    override fun getAll(difficulty: GameDifficulty, type: GameType) =
        recordDao.getAll(difficulty, type)

    override suspend fun insert(record: Record) = recordDao.insert(record)
    override suspend fun delete(record: Record) = recordDao.delete(record)
}
