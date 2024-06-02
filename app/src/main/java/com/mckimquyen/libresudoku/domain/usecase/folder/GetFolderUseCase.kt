package com.mckimquyen.libresudoku.domain.usecase.folder

import com.mckimquyen.libresudoku.domain.repository.FolderRepository
import javax.inject.Inject

class GetFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    operator fun invoke(uid: Long) = folderRepository.get(uid)
}
