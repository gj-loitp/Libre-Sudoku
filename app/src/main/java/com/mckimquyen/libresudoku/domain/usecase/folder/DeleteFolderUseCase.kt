package com.mckimquyen.libresudoku.domain.usecase.folder

import com.mckimquyen.libresudoku.data.db.model.Folder
import com.mckimquyen.libresudoku.domain.repository.FolderRepository
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folder: Folder) = folderRepository.delete(folder)
}
