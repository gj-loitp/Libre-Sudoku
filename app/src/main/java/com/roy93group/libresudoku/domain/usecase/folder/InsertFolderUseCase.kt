package com.roy93group.libresudoku.domain.usecase.folder

import com.roy93group.libresudoku.data.db.model.Folder
import com.roy93group.libresudoku.domain.repository.FolderRepository
import javax.inject.Inject

class InsertFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folder: Folder) = folderRepository.insert(folder)
}
