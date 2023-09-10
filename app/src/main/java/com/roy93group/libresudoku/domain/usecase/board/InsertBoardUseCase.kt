package com.roy93group.libresudoku.domain.usecase.board

import com.roy93group.libresudoku.data.db.model.SudokuBoard
import com.roy93group.libresudoku.domain.repository.BoardRepository
import javax.inject.Inject

class InsertBoardUseCase @Inject constructor(
    private val boardRepository: BoardRepository,
) {
    suspend operator fun invoke(board: SudokuBoard) = boardRepository.insert(board)
}
