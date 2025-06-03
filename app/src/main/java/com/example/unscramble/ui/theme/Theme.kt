/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.example.unscramble.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EasyLightColors = lightColorScheme(
    primary = easy_theme_light_primary,
    onPrimary = easy_theme_light_onPrimary,
    primaryContainer = easy_theme_light_primaryContainer,
    onPrimaryContainer = easy_theme_light_onPrimaryContainer,
    background = easy_theme_light_background,
    onBackground = easy_theme_light_onBackground,
    // .. populate with all necessary colors from easy_theme_light_*
    // secondary, onSecondary, error, onError, surface, onSurface, etc.
)

private val EasyDarkColors = darkColorScheme(
    primary = easy_theme_dark_primary,
    onPrimary = easy_theme_dark_onPrimary,
    primaryContainer = easy_theme_dark_primaryContainer,
    onPrimaryContainer = easy_theme_dark_onPrimaryContainer,
    background = easy_theme_dark_background,
    onBackground = easy_theme_dark_onBackground,
    // .. populate with all necessary colors from easy_theme_dark_*
)

// Medium Difficulty Color Schemes
private val MediumLightColors = lightColorScheme(
    primary = medium_theme_light_primary,
    onPrimary = medium_theme_light_onPrimary,
    primaryContainer = medium_theme_light_primaryContainer,
    onPrimaryContainer = medium_theme_light_onPrimaryContainer,
    background = medium_theme_light_background,
    onBackground = medium_theme_light_onBackground,
    // .. populate for medium light
)

private val MediumDarkColors = darkColorScheme(
    primary = medium_theme_dark_primary,
    onPrimary = medium_theme_dark_onPrimary,
    primaryContainer = medium_theme_dark_primaryContainer,
    onPrimaryContainer = medium_theme_dark_onPrimaryContainer,
    background = medium_theme_dark_background,
    onBackground = medium_theme_dark_onBackground,
    // .. populate for medium dark
)

// Hard Difficulty Color Schemes
private val HardLightColors = lightColorScheme(
    primary = hard_theme_light_primary,
    onPrimary = hard_theme_light_onPrimary,
    primaryContainer = hard_theme_light_primaryContainer,
    onPrimaryContainer = hard_theme_light_onPrimaryContainer,
    background = hard_theme_light_background,
    onBackground = hard_theme_light_onBackground,
    // .. populate for hard light
)

private val HardDarkColors = darkColorScheme(
    primary = hard_theme_dark_primary,
    onPrimary = hard_theme_dark_onPrimary,
    primaryContainer = hard_theme_dark_primaryContainer,
    onPrimaryContainer = hard_theme_dark_onPrimaryContainer,
    background = hard_theme_dark_background,
    onBackground = hard_theme_dark_onBackground,
    // .. populate for hard dark
)

// You might still have your default color schemes
private val DefaultLightColors = lightColorScheme(
    primary = md_theme_light_primary,
    // ... your existing default light colors
)

enum class GameDifficultyTheme() {
    EASY, MEDIUM, HARD, DEFAULT
}

@Composable
fun UnscrambleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    currentDifficulty: Int = 1,
    // Dynamic color is available on Android 12+
    // Dynamic color in this app is turned off for learning purposes
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val difficultyTheme = when (currentDifficulty) {
        1 -> GameDifficultyTheme.EASY
        2 -> GameDifficultyTheme.MEDIUM
        3 -> GameDifficultyTheme.HARD
        else -> GameDifficultyTheme.DEFAULT
    }

    val colorScheme = when {
        darkTheme -> when (difficultyTheme) { // Use the converted enum here
            GameDifficultyTheme.EASY -> EasyDarkColors
            GameDifficultyTheme.MEDIUM -> MediumDarkColors
            GameDifficultyTheme.HARD -> HardDarkColors
            else -> EasyDarkColors
        }
        else -> when (difficultyTheme) { // And here
            GameDifficultyTheme.EASY -> EasyLightColors
            GameDifficultyTheme.MEDIUM -> MediumLightColors
            GameDifficultyTheme.HARD -> HardLightColors
            else -> EasyLightColors
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}
