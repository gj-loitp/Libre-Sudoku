package com.mckimquyen.libresudoku.domain.usecase.folder

import com.mckimquyen.libresudoku.data.db.model.Folder
import com.mckimquyen.libresudoku.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoldersUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    operator fun invoke(): Flow<List<Folder>> = folderRepository.getAll()
}
