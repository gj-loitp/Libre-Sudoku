package com.kaajjo.libresudoku.ui.create_edit_sudoku

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaajjo.libresudoku.LocalBoardColors
import com.kaajjo.libresudoku.R
import com.kaajjo.libresudoku.core.PreferencesConstants
import com.kaajjo.libresudoku.core.qqwing.GameDifficulty
import com.kaajjo.libresudoku.core.qqwing.GameType
import com.kaajjo.libresudoku.ui.components.board.Board
import com.kaajjo.libresudoku.ui.game.components.DefaultGameKeyboard
import com.kaajjo.libresudoku.ui.game.components.ToolBarItem
import com.kaajjo.libresudoku.ui.game.components.ToolbarItem
import com.kaajjo.libresudoku.ui.util.ReverseArrangement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSudokuScreen(
    navigateBack: () -> Unit,
    viewModel: CreateSudokuViewModel
) {
    var importStringDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (viewModel.gameUid == -1L)
                            stringResource(R.string.create_sudoku_title)
                        else
                            stringResource(R.string.edit_sudoku)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_round_arrow_back_24),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    Box {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = null
                            )
                        }
                        MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(stringResource(R.string.create_set_from_string))
                                    },
                                    onClick = {
                                        importStringDialog = true
                                        showMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            val highlightIdentical by viewModel.highlightIdentical.collectAsState(initial = PreferencesConstants.DEFAULT_HIGHLIGHT_IDENTICAL)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Box {
                        var difficultyMenu by remember { mutableStateOf(false) }
                        val dropDownIconRotation by animateFloatAsState(if (difficultyMenu) 180f else 0f)
                        TextButton(onClick = { difficultyMenu = !difficultyMenu }) {
                            Text(stringResource(viewModel.gameDifficulty.resName))
                            Icon(
                                modifier = Modifier.rotate(dropDownIconRotation),
                                imageVector = Icons.Rounded.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                        DifficultyMenu(
                            expanded = difficultyMenu,
                            onDismissRequest = { difficultyMenu = false },
                            onClick = {
                                viewModel.changeGameDifficulty(it)
                            }
                        )
                    }
                    // allow changing a game type only when creating a new sudoku
                    if (viewModel.gameUid == -1L) {
                        Box {
                            var gameTypeMenuExpanded by remember { mutableStateOf(false) }
                            val dropDownIconRotation by animateFloatAsState(if (gameTypeMenuExpanded) 180f else 0f)
                            TextButton(onClick = { gameTypeMenuExpanded = !gameTypeMenuExpanded }) {
                                Text(stringResource(viewModel.gameType.resName))
                                Icon(
                                    modifier = Modifier.rotate(dropDownIconRotation),
                                    imageVector = Icons.Rounded.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                            GameTypeMenu(
                                expanded = gameTypeMenuExpanded,
                                onDismissRequest = { gameTypeMenuExpanded = false },
                                onClick = {
                                    viewModel.changeGameType(it)
                                }
                            )
                        }
                    }
                }
                FilledTonalButton(
                    enabled = !viewModel.gameBoard.flatten().all { it.value == 0 },
                    onClick = {
                        if (viewModel.saveGame()) {
                            navigateBack()
                        }
                    }) {
                    Text(stringResource(R.string.action_save))
                }
            }

            val fontSizeFactor by viewModel.fontSize.collectAsState(initial = PreferencesConstants.DEFAULT_FONT_SIZE_FACTOR)
            val fontSizeValue by remember(fontSizeFactor, viewModel.gameType) {
                mutableStateOf(
                    viewModel.getFontSize(factor = fontSizeFactor)
                )
            }

            val positionLines by viewModel.positionLines.collectAsState(initial = PreferencesConstants.DEFAULT_POSITION_LINES)
            val crossHighlight by viewModel.crossHighlight.collectAsState(initial = PreferencesConstants.DEFAULT_BOARD_CROSS_HIGHLIGHT)
            Board(
                modifier = Modifier.padding(vertical = 12.dp),
                size = viewModel.gameType.size,
                mainTextSize = fontSizeValue,
                board = viewModel.gameBoard,
                selectedCell = viewModel.currCell,
                onClick = { cell ->
                    viewModel.processInput(cell = cell)
                },
                identicalNumbersHighlight = highlightIdentical,
                boardColors = LocalBoardColors.current,
                positionLines = positionLines,
                crossHighlight = crossHighlight
            )

            val funKeyboardOverNum by viewModel.funKeyboardOverNum.collectAsStateWithLifecycle(
                initialValue = PreferencesConstants.DEFAULT_FUN_KEYBOARD_OVER_NUM
            )

            Column(
                verticalArrangement = if (funKeyboardOverNum) ReverseArrangement else Arrangement.Top
            ) {
                DefaultGameKeyboard(
                    size = viewModel.gameType.size,
                    remainingUses = null,
                    onClick = {
                        viewModel.processInputKeyboard(number = it)
                    },
                    onLongClick = {
                        viewModel.processInputKeyboard(
                            number = it,
                            longTap = true
                        )
                    },
                    selected = viewModel.digitFirstNumber
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    ToolbarItem(
                        modifier = Modifier.weight(1f),
                        painter = painterResource(R.drawable.ic_round_undo_24),
                        onClick = { viewModel.toolbarClick(ToolBarItem.Undo) }
                    )
                    ToolbarItem(
                        modifier = Modifier.weight(1f),
                        painter = painterResource(R.drawable.ic_eraser_24),
                        onClick = {
                            viewModel.toolbarClick(ToolBarItem.Remove)
                        }
                    )
                }
            }

            if (importStringDialog) {
                ImportStringSudokuDialog(
                    textValue = viewModel.importStringValue,
                    onTextChange = {
                        viewModel.importStringValue = it
                        viewModel.importTextFieldError = false
                    },
                    isError = viewModel.importTextFieldError,
                    onConfirm = {
                        viewModel.setFromString(viewModel.importStringValue.trim()).also {
                            viewModel.importTextFieldError = !it
                            if (it) {
                                importStringDialog = false
                                viewModel.importStringValue = ""
                            }
                        }
                    },
                    onDismiss = { importStringDialog = false }
                )
            } else if (viewModel.multipleSolutionsDialog) {
                AlertDialog(
                    title = { Text(stringResource(R.string.create_incorrect_puzzle)) },
                    text = {
                        Text(stringResource(R.string.multiple_solution_text))
                    },
                    onDismissRequest = { viewModel.multipleSolutionsDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.multipleSolutionsDialog = false
                        }) {
                            Text(stringResource(R.string.dialog_ok))
                        }
                    }
                )
            } else if (viewModel.noSolutionsDialog) {
                AlertDialog(
                    title = { Text(stringResource(R.string.create_incorrect_puzzle)) },
                    text = {
                        Text(stringResource(R.string.no_solution_text))
                    },
                    onDismissRequest = { viewModel.noSolutionsDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.noSolutionsDialog = false
                        }) {
                            Text(stringResource(R.string.dialog_ok))
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun GameTypeMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (GameType) -> Unit
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            listOf(
                GameType.Default9x9,
                GameType.Default6x6,
                GameType.Default12x12,
            ).forEach {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(it.resName))
                    },
                    onClick = {
                        onClick(it)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImportStringSudokuDialog(
    textValue: String,
    onTextChange: (String) -> Unit,
    isError: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val focusRequester = FocusRequester()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    AlertDialog(
        title = { Text(stringResource(R.string.create_set_from_string)) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.create_from_string_text))
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .focusRequester(focusRequester),
                    value = textValue,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onConfirm() }
                    ),
                    isError = isError,
                    onValueChange = onTextChange,
                    label = { Text(stringResource(R.string.create_from_string_hint)) }
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.action_cancel))
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.create_import_set))
            }
        }
    )
}

@Composable
private fun DifficultyMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (GameDifficulty) -> Unit
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            listOf(
                GameDifficulty.Easy,
                GameDifficulty.Moderate,
                GameDifficulty.Hard,
                GameDifficulty.Challenge,
                GameDifficulty.Custom,
            ).forEach {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(it.resName))
                    },
                    onClick = {
                        onClick(it)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}