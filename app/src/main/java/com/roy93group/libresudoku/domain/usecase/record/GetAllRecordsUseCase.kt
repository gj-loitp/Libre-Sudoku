package com.roy93group.libresudoku.domain.usecase.record

import com.roy93group.libresudoku.core.qqwing.GameDifficulty
import com.roy93group.libresudoku.core.qqwing.GameType
import com.roy93group.libresudoku.domain.repository.RecordRepository
import javax.inject.Inject

class GetAllRecordsUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {
    operator fun invoke(difficulty: GameDifficulty, type: GameType) = recordRepository.getAll(difficulty, type)
}