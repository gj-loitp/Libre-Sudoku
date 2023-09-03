package com.roy93group.libresudoku.domain.usecase.folder

import com.roy93group.libresudoku.data.database.model.Folder
import com.roy93group.libresudoku.domain.repository.FolderRepository
import javax.inject.Inject

class UpdateFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(folder: Folder) = folderRepository.update(folder)
}