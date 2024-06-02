package com.mckimquyen.libresudoku.ui.learn.learnSudoku

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mckimquyen.libresudoku.LocalBoardColors
import com.mckimquyen.libresudoku.R
import com.mckimquyen.libresudoku.core.Cell
import com.mckimquyen.libresudoku.core.Note
import com.mckimquyen.libresudoku.core.qqwing.GameType
import com.mckimquyen.libresudoku.core.utils.SudokuParser
import com.mckimquyen.libresudoku.ui.components.board.Board
import com.mckimquyen.libresudoku.ui.learn.components.TutorialBase
import com.mckimquyen.libresudoku.ui.learn.components.TutorialBottomContent

@Composable
fun LearnHiddenPairs(
    helpNavController: NavController,
) {
    TutorialBase(
        title = stringResource(R.string.learn_hidden_pairs_title),
        helpNavController = helpNavController
    ) {
        val sudokuParser = SudokuParser()
        val board by remember {
            mutableStateOf(
                sudokuParser.parseBoard(
                    board = "..............................3.1.......2........................................",
                    gameType = GameType.Default9x9,
                    emptySeparator = '.'
                ).toList()
            )
        }
        var notes by remember {
            mutableStateOf(
                listOf(
                    Note(3, 4, 4),
                    Note(3, 4, 5),
                    Note(3, 4, 8),
                    Note(4, 3, 4),
                    Note(4, 3, 5),
                    Note(4, 3, 7),
                    Note(4, 5, 4),
                    Note(row = 4, col = 5, value = 5),
                    Note(5, 3, 6),
                    Note(5, 3, 7),
                    Note(5, 3, 8),
                    Note(5, 3, 9),
                    Note(5, 4, 7),
                    Note(5, 4, 8),
                    Note(5, 5, 6),
                    Note(5, 5, 8),
                    Note(5, 5, 9),
                )
            )
        }
        val steps = listOf(
            stringResource(R.string.learn_hidden_pairs_1),
            stringResource(R.string.learn_hidden_pairs_2)
        )
        val stepsCell = listOf(
            listOf(Cell(row = 5, col = 3), Cell(row = 5, col = 5))
        )
        var step by remember { mutableIntStateOf(0) }
        LaunchedEffect(key1 = step) {
            when (step) {
                0 -> {
                    notes = listOf(
                        Note(row = 3, col = 4, value = 4),
                        Note(3, 4, 5),
                        Note(3, 4, 8),
                        Note(4, 3, 4),
                        Note(4, 3, 5),
                        Note(4, 3, 7),
                        Note(4, 5, 4),
                        Note(4, 5, 5),
                        Note(5, 3, 6),
                        Note(5, 3, 7),
                        Note(5, 3, 8),
                        Note(5, 3, 9),
                        Note(5, 4, 7),
                        Note(5, 4, 8),
                        Note(5, 5, 6),
                        Note(5, 5, 8),
                        Note(5, 5, 9)
                    )
                }

                1 -> {
                    notes = listOf(
                        Note(3, 4, 4),
                        Note(3, 4, 5),
                        Note(3, 4, 8),
                        Note(4, 3, 4),
                        Note(4, 3, 5),
                        Note(4, 3, 7),
                        Note(4, 5, 4),
                        Note(4, 5, 5),
                        Note(5, 3, 6),
                        Note(5, 3, 9),
                        Note(5, 4, 7),
                        Note(5, 4, 8),
                        Note(5, 5, 6),
                        Note(5, 5, 9)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Board(
                board = board,
                notes = notes,
                cellsToHighlight = if (step < stepsCell.size) stepsCell[step] else null,
                onClick = { },
                selectedCell = Cell(-1, -1),
                boardColors = LocalBoardColors.current
            )
            TutorialBottomContent(
                steps = steps,
                step = step,
                onPreviousClick = {
                    if (step > 0) step--
                },
                onNextClick = {
                    if (step < (steps.size - 1)) step++
                }
            )
        }
    }
}
