package com.roy93group.libresudoku.domain.usecase.folder

import com.roy93group.libresudoku.domain.repository.FolderRepository
import javax.inject.Inject

class CountPuzzlesFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    operator fun invoke(uid: Long) = folderRepository.countPuzzlesFolder(uid)
}