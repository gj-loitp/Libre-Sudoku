package com.mckimquyen.libresudoku.domain.usecase

import com.mckimquyen.libresudoku.data.db.model.SudokuBoard
import com.mckimquyen.libresudoku.domain.repository.BoardRepository
import javax.inject.Inject

class UpdateManyBoardsUseCase @Inject constructor(
    private val boardRepository: BoardRepository,
) {
    suspend operator fun invoke(boards: List<SudokuBoard>) = boardRepository.update(boards)
}
