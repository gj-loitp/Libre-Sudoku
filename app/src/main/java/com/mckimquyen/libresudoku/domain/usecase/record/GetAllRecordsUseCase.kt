package com.mckimquyen.libresudoku.domain.usecase.record

import com.mckimquyen.libresudoku.core.qqwing.GameDifficulty
import com.mckimquyen.libresudoku.core.qqwing.GameType
import com.mckimquyen.libresudoku.domain.repository.RecordRepository
import javax.inject.Inject

class GetAllRecordsUseCase @Inject constructor(
    private val recordRepository: RecordRepository,
) {
    operator fun invoke(difficulty: GameDifficulty, type: GameType) =
        recordRepository.getAll(difficulty, type)
}
