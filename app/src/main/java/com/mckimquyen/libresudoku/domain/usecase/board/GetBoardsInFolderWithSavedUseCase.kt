package com.mckimquyen.libresudoku.domain.usecase.board

import com.mckimquyen.libresudoku.domain.repository.BoardRepository
import javax.inject.Inject

class GetBoardsInFolderWithSavedUseCase @Inject constructor(
    private val boardRepository: BoardRepository,
) {
    operator fun invoke(folderUid: Long) = boardRepository.getInFolderWithSaved(folderUid)
}
