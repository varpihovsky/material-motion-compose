/*
 * Copyright 2021 SOUP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("unused")

package soup.compose.material.motion

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private const val ProgressThreshold = 0.35f

private val Int.ForOutgoing: Int
    get() = (this * ProgressThreshold).toInt()

private val Int.ForIncoming: Int
    get() = this - this.ForOutgoing

/**
 * [materialFadeThrough] allows to switch a layout with a fade through animation.
 *
 * @param durationMillis the duration of transition.
 */
@ExperimentalAnimationApi
fun materialFadeThrough(
    durationMillis: Int = MotionConstants.motionDurationLong1,
): MotionSpec = materialFadeThroughIn(
    durationMillis = durationMillis
) with materialFadeThroughOut(
    durationMillis = durationMillis
)

/**
 * [materialFadeThroughIn] allows to switch a layout with fade through enter transition.
 *
 * @param initialScale the starting scale of the enter transition.
 * @param durationMillis the duration of the enter transition.
 */
@ExperimentalAnimationApi
fun materialFadeThroughIn(
    initialScale: Float = 0.92f,
    durationMillis: Int = MotionConstants.motionDurationLong1,
): EnterMotionSpec = EnterMotionSpec(
    transition = fadeIn(
        animationSpec = tween(
            durationMillis = durationMillis.ForIncoming,
            delayMillis = durationMillis.ForOutgoing,
            easing = LinearOutSlowInEasing
        )
    ),
    transitionExtra = scaleIn(
        initialScale = initialScale,
        animationSpec = tween(
            durationMillis = durationMillis.ForIncoming,
            delayMillis = durationMillis.ForOutgoing,
            easing = LinearOutSlowInEasing
        )
    )
)

/**
 * [materialFadeThroughOut] allows to switch a layout with fade through exit transition.
 *
 * @param durationMillis the duration of the exit transition.
 */
@ExperimentalAnimationApi
fun materialFadeThroughOut(
    durationMillis: Int = MotionConstants.motionDurationLong1,
): ExitMotionSpec = ExitMotionSpec(
    transition = fadeOut(
        animationSpec = tween(
            durationMillis = durationMillis.ForOutgoing,
            delayMillis = 0,
            easing = FastOutLinearInEasing
        )
    )
)

/**
 * [MaterialFadeThrough] allows to switch between two layouts with a fade through animation.
 *
 * @see com.google.android.material.transition.MaterialFadeThrough
 *
 * @param targetState is a key representing your target layout state. Every time you change a key
 * the animation will be triggered. The [content] called with the old key will be faded out while
 * the [content] called with the new key will be faded in.
 * @param modifier Modifier to be applied to the animation container.
 */
@ExperimentalAnimationApi
@Composable
fun <T> MaterialFadeThrough(
    targetState: T,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    MaterialMotion(
        targetState = targetState,
        motionSpec = materialFadeThroughIn() with materialFadeThroughOut(),
        modifier = modifier,
        content = content
    )
}
