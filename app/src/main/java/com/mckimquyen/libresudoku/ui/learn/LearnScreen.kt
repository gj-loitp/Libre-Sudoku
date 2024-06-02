package com.mckimquyen.libresudoku.ui.learn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mckimquyen.libresudoku.R
import com.mckimquyen.libresudoku.ui.learn.learnApp.LearnAppScreen
import com.mckimquyen.libresudoku.ui.learn.learnApp.ToolbarTutorialScreen
import com.mckimquyen.libresudoku.ui.learn.learnSudoku.*
import kotlinx.coroutines.launch

@Composable
fun LearnScreen(
    navigateBack: () -> Unit,
) {
    val helpNavController = rememberNavController()
    NavHost(navController = helpNavController, startDestination = "help") {
        composable("help") {
            LearnScreenContent(
                navigateBack = navigateBack,
                helpNavController = helpNavController
            )
        }
        composable("app_toolbar") { ToolbarTutorialScreen(helpNavController = helpNavController) }
        composable("sudoku_rules") { LearnSudokuRules(helpNavController = helpNavController) }
        composable("sudoku_basic") { LearnBasic(helpNavController = helpNavController) }
        composable("sudoku_naked_pairs") { LearnNakedPairs(helpNavController = helpNavController) }
        composable("sudoku_hidden_pairs") { LearnHiddenPairs(helpNavController = helpNavController) }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
fun LearnScreenContent(
    navigateBack: () -> Unit,
    helpNavController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.learn_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_round_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val context = LocalContext.current
            val pagerState = rememberPagerState()
            val pages by remember {
                mutableStateOf(
                    listOf(
                        context.getString(R.string.learn_tab_sudoku),
                        context.getString(R.string.learn_tab_app)
                    )
                )
            }
            val coroutineScope = rememberCoroutineScope()
            TabRow(selectedTabIndex = pagerState.currentPage) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index, pageOffset = 0f)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxHeight(),
                count = pages.size,
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { page ->
                when (page) {
                    0 -> LearnSudokuScreen(helpNavController)
                    1 -> LearnAppScreen(helpNavController)
                    else -> LearnSudokuScreen(helpNavController)
                }
            }
        }
    }
}
