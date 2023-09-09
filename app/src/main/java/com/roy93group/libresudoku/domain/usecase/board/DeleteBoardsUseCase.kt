package com.roy93group.libresudoku.domain.usecase.board

import com.roy93group.libresudoku.data.database.model.SudokuBoard
import com.roy93group.libresudoku.domain.repository.BoardRepository
import javax.inject.Inject

class DeleteBoardsUseCase @Inject constructor(
    private val boardRepository: BoardRepository,
) {
    suspend operator fun invoke(boards: List<SudokuBoard>) = boardRepository.delete(boards)
}
