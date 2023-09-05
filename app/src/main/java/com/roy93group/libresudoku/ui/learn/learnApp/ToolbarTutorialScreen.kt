package com.roy93group.libresudoku.ui.learn.learnApp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.roy93group.libresudoku.R
import com.roy93group.libresudoku.ui.learn.components.TutorialBase
import com.roy93group.libresudoku.ui.onboarding.FirstGameScreen

@Composable
fun ToolbarTutorialScreen(helpNavController: NavController) {
    TutorialBase(
        title = stringResource(R.string.learn_app_toolbar),
        helpNavController = helpNavController
    ) {
        FirstGameScreen()
        Text(
            text = stringResource(R.string.learn_app_toolbar_notes_menu),
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
