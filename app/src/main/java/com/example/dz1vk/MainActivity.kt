package com.example.dz1vk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NumberGridScreen()
                }
            }
        }
    }
}

@Composable
fun NumberGridScreen() {
    val initialCount = integerResource(R.integer.initial_count)
    val columns = integerResource(R.integer.column_count)
    val firstNumber = integerResource(R.integer.first_number)

    var count by rememberSaveable {
        mutableIntStateOf(initialCount)
    }

    val spacing = dimensionResource(R.dimen.grid_spacing)
    val screenPadding = dimensionResource(R.dimen.screen_padding)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(screenPadding),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            items(
                count = count,
                key = { index -> index }
            ) { index ->
                NumberSquare(number = index + firstNumber)
            }
        }

        Button(
            onClick = { count++ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.add_number))
        }
    }
}

@Composable
fun NumberSquare(number: Int) {
    val background = colorResource(
        if (number % 2 == 0) {
            R.color.even_background
        } else {
            R.color.odd_background
        }
    )

    val fontSize = TextUnit(
        resourcesFontSize(),
        TextUnitType.Sp
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.number_format, number),
            color = colorResource(R.color.number_text),
            fontSize = fontSize
        )
    }
}

@Composable
private fun resourcesFontSize(): Float {
    val resources = androidx.compose.ui.platform.LocalContext.current.resources
    return resources.getDimension(R.dimen.number_text_size) /
            resources.displayMetrics.scaledDensity
}
