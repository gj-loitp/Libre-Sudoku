package com.roy93group.libresudoku.ui.components.collapsingTopAppBar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Float.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTopAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    centralContent: (@Composable () -> Unit)? = null,
    additionalContent: (@Composable () -> Unit)? = null,
    collapsingTitle: CollapsingTitle? = null,
    scrollBehavior: CollapsingTopAppBarScrollBehavior? = null,
    collapsedElevation: Dp = defaultCollapsedElevation,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
) {
    val collapsedFraction = when {
        scrollBehavior != null && centralContent == null -> scrollBehavior.state.collapsedFraction
        scrollBehavior != null && centralContent != null -> 0f
        else -> 1f
    }

    val fullyCollapsedTitleScale = when {
        collapsingTitle != null -> collapsedTitleLineHeight.value / collapsingTitle.expandedTextStyle.lineHeight.value
        else -> 1f
    }

    val collapsingTitleScale = lerp(1f, fullyCollapsedTitleScale, collapsedFraction)

    val showElevation = when {
        scrollBehavior == null -> false
        scrollBehavior.state.contentOffset <= 0 && collapsedFraction == 1f -> true
        scrollBehavior.state.contentOffset < -1f && centralContent != null -> true
        else -> false
    }

    val containerColor = animateColorAsState(
        if (showElevation) MaterialTheme.colorScheme.surfaceColorAtElevation(collapsedElevation)
        else MaterialTheme.colorScheme.surface, label = ""
    )

    Surface(
        modifier = modifier,
        color = containerColor.value
    ) {
        Layout(
            content = {
                if (collapsingTitle != null) {
                    Text(
                        modifier = Modifier
                            .layoutId(EXPANDED_TITLE_ID)
                            .wrapContentHeight(align = Alignment.Top)
                            .graphicsLayer(
                                scaleX = collapsingTitleScale,
                                scaleY = collapsingTitleScale,
                                transformOrigin = TransformOrigin(
                                    pivotFractionX = 0f,
                                    pivotFractionY = 0f
                                )
                            ),
                        text = collapsingTitle.titleText,
                        style = collapsingTitle.expandedTextStyle,
                        color = collapsingTitle.color
                    )
                    Text(
                        modifier = Modifier
                            .layoutId(COLLAPSED_TITLE_ID)
                            .wrapContentHeight(align = Alignment.Top)
                            .graphicsLayer(
                                scaleX = collapsingTitleScale,
                                scaleY = collapsingTitleScale,
                                transformOrigin = TransformOrigin(
                                    pivotFractionX = 0f,
                                    pivotFractionY = 0f
                                )
                            ),
                        text = collapsingTitle.titleText,
                        style = collapsingTitle.expandedTextStyle,
                        color = collapsingTitle.color,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (navigationIcon != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(NAVIGATION_ICON_ID)
                    ) {
                        navigationIcon()
                    }
                }

                if (actions != null) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(ACTIONS_ID)
                    ) {
                        actions()
                    }
                }

                if (centralContent != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(CENTRAL_CONTENT_ID)
                    ) {
                        centralContent()
                    }
                }

                if (additionalContent != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .layoutId(ADDITIONAL_CONTENT_ID)
                    ) {
                        additionalContent()
                    }
                }
            },
            modifier = modifier
                .windowInsetsPadding(windowInsets)
                .then(Modifier.heightIn(min = minCollapsedHeight))
        ) { measurables, constraints ->
            val horizontalPaddingPx = horizontalPadding.toPx()
            val expandedTitleStartPaddingPx = expandedTitleStartPadding.toPx()
            val expandedTitleBottomPaddingPx = expandedTitleBottomPadding.toPx()
            val expandedTitleTopPadding = expandedTitleTopPadding.toPx()


            // Measuring widgets inside TopAppBar:

            val navigationIconPlaceable =
                measurables.firstOrNull { it.layoutId == NAVIGATION_ICON_ID }
                    ?.measure(constraints.copy(minWidth = 0))

            val actionsPlaceable = measurables.firstOrNull { it.layoutId == ACTIONS_ID }
                ?.measure(constraints.copy(minWidth = 0))

            val expandedTitlePlaceable = measurables.firstOrNull { it.layoutId == EXPANDED_TITLE_ID }
                ?.measure(
                    constraints.copy(
                        maxWidth = (constraints.maxWidth - 2 * horizontalPaddingPx).roundToInt(),
                        minWidth = 0,
                        minHeight = 0
                    )
                )

            val additionalContentPlaceable =
                measurables.firstOrNull { it.layoutId == ADDITIONAL_CONTENT_ID }
                    ?.measure(constraints)

            val navigationIconOffset = when (navigationIconPlaceable) {
                null -> horizontalPaddingPx
                else -> navigationIconPlaceable.width + horizontalPaddingPx * 2
            }

            val actionsOffset = when (actionsPlaceable) {
                null -> horizontalPaddingPx
                else -> actionsPlaceable.width + horizontalPaddingPx * 2
            }

            val collapsedTitleMaxWidthPx =
                (constraints.maxWidth - navigationIconOffset - actionsOffset) / fullyCollapsedTitleScale

            val collapsedTitlePlaceable =
                measurables.firstOrNull { it.layoutId == COLLAPSED_TITLE_ID }
                    ?.measure(
                        constraints.copy(
                            maxWidth = collapsedTitleMaxWidthPx.roundToInt(),
                            minWidth = 0,
                            minHeight = 0
                        )
                    )

            val centralContentPlaceable =
                measurables.firstOrNull { it.layoutId == CENTRAL_CONTENT_ID }
                    ?.measure(
                        constraints.copy(
                            minWidth = 0,
                            maxWidth = (constraints.maxWidth - navigationIconOffset - actionsOffset).roundToInt()
                        )
                    )

            val collapsedHeightPx = when {
                centralContentPlaceable != null ->
                    max(minCollapsedHeight.toPx(), centralContentPlaceable.height.toFloat())

                else -> minCollapsedHeight.toPx()
            }

            var layoutHeightPx = collapsedHeightPx


            // Calculating coordinates of widgets inside TopAppBar:

            // Current coordinates of navigation icon
            val navigationIconX = horizontalPaddingPx.roundToInt()
            val navigationIconY =
                ((collapsedHeightPx - (navigationIconPlaceable?.height ?: 0)) / 2).roundToInt()

            // Current coordinates of actions
            val actionsX = (constraints.maxWidth - (actionsPlaceable?.width
                ?: 0) - horizontalPaddingPx).roundToInt()
            val actionsY = ((collapsedHeightPx - (actionsPlaceable?.height ?: 0)) / 2).roundToInt()

            // Current coordinates of title
            var collapsingTitleY = 0
            var collapsingTitleX = 0

            if (expandedTitlePlaceable != null && collapsedTitlePlaceable != null) {
                // Measuring TopAppBar collapsing distance
                val heightOffsetLimitPx =
                    expandedTitleTopPadding + expandedTitlePlaceable.height + expandedTitleBottomPaddingPx
                scrollBehavior?.state?.heightOffsetLimit = when (centralContent) {
                    null -> -heightOffsetLimitPx
                    else -> -1f
                }

                // TopAppBar height at fully expanded state
                val fullyExpandedHeightPx = minCollapsedHeight.toPx() + heightOffsetLimitPx

                // Coordinates of fully expanded title
                val fullyExpandedTitleY =
                    fullyExpandedHeightPx - expandedTitlePlaceable.height - expandedTitleBottomPaddingPx

                // Coordinates of fully collapsed title
                val fullyCollapsedTitleY =
                    collapsedHeightPx / 2 - collapsedTitleLineHeight.toPx().roundToInt() / 2

                // Current height of TopAppBar
                layoutHeightPx = lerp(fullyExpandedHeightPx, collapsedHeightPx, collapsedFraction)

                // Current coordinates of collapsing title
                collapsingTitleX =
                    lerp(expandedTitleStartPaddingPx, navigationIconOffset, collapsedFraction).roundToInt()
                collapsingTitleY =
                    lerp(fullyExpandedTitleY, fullyCollapsedTitleY, collapsedFraction).roundToInt()
            } else {
                scrollBehavior?.state?.heightOffsetLimit = -1f
            }

            val topAppBarHeightPx =
                layoutHeightPx.roundToInt() + (additionalContentPlaceable?.height ?: 0)


            // Placing TopAppBar widgets:

            layout(constraints.maxWidth, topAppBarHeightPx) {
                navigationIconPlaceable?.placeRelative(
                    x = navigationIconX,
                    y = navigationIconY
                )
                actionsPlaceable?.placeRelative(
                    x = actionsX,
                    y = actionsY
                )
                centralContentPlaceable?.placeRelative(
                    x = navigationIconOffset.roundToInt(),
                    y = ((collapsedHeightPx - centralContentPlaceable.height) / 2).roundToInt()
                )
                if (expandedTitlePlaceable?.width == collapsedTitlePlaceable?.width) {
                    expandedTitlePlaceable?.placeRelative(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                    )
                } else {
                    expandedTitlePlaceable?.placeRelativeWithLayer(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                        layerBlock = { alpha = 1 - collapsedFraction }
                    )
                    collapsedTitlePlaceable?.placeRelativeWithLayer(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                        layerBlock = { alpha = collapsedFraction }
                    )
                }
                additionalContentPlaceable?.placeRelative(
                    x = 0,
                    y = layoutHeightPx.roundToInt()
                )
            }
        }

    }
}


private fun lerp(a: Float, b: Float, fraction: Float): Float {
    return a + fraction * (b - a)
}

data class CollapsingTitle(
    val titleText: String,
    val expandedTextStyle: TextStyle,
    val color: Color,
) {

    companion object {
        @Composable
        fun large(titleText: String) =
            CollapsingTitle(
                titleText,
                MaterialTheme.typography.headlineLarge,
                MaterialTheme.colorScheme.onSurface
            )

        @Composable
        fun medium(titleText: String) =
            CollapsingTitle(
                titleText,
                MaterialTheme.typography.headlineMedium,
                MaterialTheme.colorScheme.onSurface
            )
    }

}

private val minCollapsedHeight = 64.dp
private val horizontalPadding = 4.dp
private val expandedTitleBottomPadding = 26.dp
private val expandedTitleTopPadding = 12.dp
private val expandedTitleStartPadding = 16.dp
private val collapsedTitleLineHeight = 28.sp
private val defaultCollapsedElevation = 3.0.dp

private const val EXPANDED_TITLE_ID = "expandedTitle"
private const val COLLAPSED_TITLE_ID = "collapsedTitle"
private const val NAVIGATION_ICON_ID = "navigationIcon"
private const val ACTIONS_ID = "actions"
private const val CENTRAL_CONTENT_ID = "centralContent"
private const val ADDITIONAL_CONTENT_ID = "additionalContent"