package com.roy93group.libresudoku.domain.usecase

import com.roy93group.libresudoku.data.database.model.SudokuBoard
import com.roy93group.libresudoku.domain.repository.BoardRepository
import javax.inject.Inject

class UpdateManyBoardsUseCase @Inject constructor(
    private val boardRepository: BoardRepository,
) {
    suspend operator fun invoke(boards: List<SudokuBoard>) = boardRepository.update(boards)
}
