package com.mckimquyen.libresudoku.domain.usecase.folder

import com.mckimquyen.libresudoku.data.db.model.Folder
import com.mckimquyen.libresudoku.domain.repository.FolderRepository
import javax.inject.Inject

class InsertFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folder: Folder) = folderRepository.insert(folder)
}
