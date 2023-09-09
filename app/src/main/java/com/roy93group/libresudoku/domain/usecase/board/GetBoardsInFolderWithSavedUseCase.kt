package com.roy93group.libresudoku.domain.usecase.board

import com.roy93group.libresudoku.domain.repository.BoardRepository
import javax.inject.Inject

class GetBoardsInFolderWithSavedUseCase @Inject constructor(
    private val boardRepository: BoardRepository,
) {
    operator fun invoke(folderUid: Long) = boardRepository.getInFolderWithSaved(folderUid)
}
