package raf.console.zickreee.components


import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

object Transitions {
    val DefaultTransitionIn = fadeIn(tween(300))
    val DefaultTransitionOut = fadeOut(tween(300))

    val FadeTransitionIn = fadeIn(tween(400)) + scaleIn(tween(400), initialScale = 0.975f)
    val FadeTransitionOut = fadeOut(tween(100))

    val SlidingTransitionIn = fadeIn(tween(400)) +
            slideInHorizontally(tween(400)) { it / 16 }
    val BackSlidingTransitionIn = fadeIn(tween(400)) +
            slideInHorizontally(tween(400)) { -it / 16 }

    val SlidingTransitionOut = fadeOut(tween(300)) +
            slideOutHorizontally(tween(400)) { -it / 16 }
    val BackSlidingTransitionOut = fadeOut(tween(300)) +
            slideOutHorizontally(tween(400)) { it / 16 }

    val NoEnterAnimation = fadeIn(tween(0))
    val NoExitAnimation = fadeOut(tween(0))
}

@Composable
fun DefaultTransition(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = Transitions.DefaultTransitionIn,
        exit = Transitions.DefaultTransitionOut
    ) {
        content()
    }
}

@Composable
fun SlidingTransition(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = slideInVertically(tween(300)) {
            with(density) { -12.dp.toPx() }.roundToInt()
        } + fadeIn(tween(300)),
        exit = slideOutVertically(tween(150)) {
            with(density) { -10.dp.toPx() }.roundToInt()
        } + fadeOut(tween(100)),
        content = content
    )
}

@Composable
fun ExpandingTransition(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = expandVertically(tween(400)) +
                fadeIn(tween(400)),
        exit = shrinkVertically(tween(350)) +
                fadeOut(tween(200)),
        content = content
    )
}

@Composable
fun HorizontalExpandingTransition(
    visible: Boolean,
    startDirection: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val enterAnimation = remember(startDirection) {
        if (startDirection) expandHorizontally(expandFrom = Alignment.Start) + fadeIn() + slideInHorizontally { -it }
        else expandHorizontally() + fadeIn() + slideInHorizontally { it }
    }
    val exitAnimation = remember(startDirection) {
        if (startDirection) shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut() + slideOutHorizontally { -it }
        else shrinkHorizontally() + fadeOut() + slideOutHorizontally { it }
    }

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enterAnimation,
        exit = exitAnimation
    ) {
        content()
    }
}

@Composable
fun FadeTransitionPreservingSpace(
    visible: Boolean,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 300, easing = EaseInOut),
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        if (visible) 1f else 0f,
        label = "",
        animationSpec = animationSpec
    )

    Box(modifier = modifier.alpha(alpha)) {
        content.invoke()
    }
}