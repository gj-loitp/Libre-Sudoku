package com.roy93group.libresudoku.domain.usecase.folder

import com.roy93group.libresudoku.data.db.model.Folder
import com.roy93group.libresudoku.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoldersUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    operator fun invoke(): Flow<List<Folder>> = folderRepository.getAll()
}
