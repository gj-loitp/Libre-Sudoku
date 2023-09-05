package com.roy93group.libresudoku.ui.gamesHistory.savedGame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roy93group.libresudoku.core.Cell
import com.roy93group.libresudoku.core.Note
import com.roy93group.libresudoku.core.utils.SudokuParser
import com.roy93group.libresudoku.core.utils.SudokuUtils
import com.roy93group.libresudoku.data.database.model.Folder
import com.roy93group.libresudoku.data.database.model.SavedGame
import com.roy93group.libresudoku.data.database.model.SudokuBoard
import com.roy93group.libresudoku.data.datastore.AppSettingsManager
import com.roy93group.libresudoku.data.datastore.ThemeSettingsManager
import com.roy93group.libresudoku.domain.repository.BoardRepository
import com.roy93group.libresudoku.domain.repository.SavedGameRepository
import com.roy93group.libresudoku.domain.usecase.folder.GetFolderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class SavedGameViewModel
@Inject constructor(
    private val boardRepository: BoardRepository,
    private val savedGameRepository: SavedGameRepository,
    private val getFolderUseCase: GetFolderUseCase,
    appSettingsManager: AppSettingsManager,
    themeSettingsManager: ThemeSettingsManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val boardUid = savedStateHandle.get<Long>("uid")

    val fontSize = appSettingsManager.fontSize
    val dateFormat = appSettingsManager.dateFormat

    var savedGame by mutableStateOf<SavedGame?>(null)
    var boardEntity by mutableStateOf<SudokuBoard?>(null)

    var parsedInitialBoard by mutableStateOf(emptyList<List<Cell>>())
    var parsedCurrentBoard by mutableStateOf(emptyList<List<Cell>>())
    var notes by mutableStateOf(emptyList<Note>())

    var exportDialog by mutableStateOf(false)

    private val _gameFolder: MutableStateFlow<Folder?> = MutableStateFlow(null)
    val gameFolder = _gameFolder.asStateFlow()

    private val _gameProgressPercentage = MutableStateFlow(0)
    val gameProgressPercentage = _gameProgressPercentage.asStateFlow()

    val crossHighlight = themeSettingsManager.boardCrossHighlight

    fun updateGameDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            boardEntity = boardRepository.get(boardUid ?: 0)
            savedGame = savedGameRepository.get(boardUid ?: 0)

            boardEntity?.let { boardEntity ->
                savedGame?.let { savedGame ->
                    withContext(Dispatchers.Default) {
                        val sudokuParser = SudokuParser()
                        parsedInitialBoard =
                            sudokuParser.parseBoard(
                                board = boardEntity.initialBoard,
                                gameType = boardEntity.type,
                                locked = true
                            )
                        parsedCurrentBoard =
                            sudokuParser.parseBoard(
                                board = savedGame.currentBoard,
                                gameType = boardEntity.type
                            )
                                .onEach { cells ->
                                    cells.forEach { cell ->
                                        cell.locked =
                                            parsedInitialBoard[cell.row][cell.col].value != 0
                                    }
                                }
                        notes = sudokuParser.parseNotes(savedGame.notes)
                    }
                }

                viewModelScope.launch {
                    boardEntity.folderId?.let { folderUid ->
                        val folder = getFolderUseCase(folderUid)
                        folder.collectLatest {
                            _gameFolder.emit(it)
                        }
                    }
                }
            }
        }
    }

    fun countProgressFilled() {
        viewModelScope.launch {
            var totalCells = 1
            var count = 0
            boardEntity?.let { board ->
                totalCells = (board.type.sectionWidth * board.type.sectionHeight)
                    .toDouble()
                    .pow(2.0)
                    .toInt()
                count =
                    totalCells - parsedCurrentBoard.sumOf { cells -> cells.count { cell -> cell.value == 0 } }
            }
            _gameProgressPercentage.emit((count.toFloat() / totalCells.toFloat() * 100f).toInt())
        }
    }

    fun getFontSize(factor: Int): TextUnit {
        boardEntity?.let {
            val sudokuUtils = SudokuUtils()
            return sudokuUtils.getFontSize(type = it.type, factor = factor)
        }
        return 24.sp
    }
}
