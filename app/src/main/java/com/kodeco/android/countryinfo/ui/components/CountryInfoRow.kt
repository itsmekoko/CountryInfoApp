package com.kodeco.android.countryinfo.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModel

@Composable
fun CountryInfoRow(
    country: Country,
    index: Int,
    viewModel: CountryInfoViewModel,
    onClick: (Int) -> Unit,
) {
    val favoriteOutlineIcon = painterResource(id = R.drawable.favourite_outline)
    val favoriteFilledIcon = painterResource(id = R.drawable.favourite_filled)

    val iconSize by animateDpAsState(
        targetValue = if (country.isFavorite) 38.dp else 30.dp,
        animationSpec = tween(500)
    )

    val rotationState = remember { mutableFloatStateOf(0f) }
    val animatedRotation by animateFloatAsState(
        targetValue = rotationState.floatValue,
        animationSpec = tween(300)
    )

    LaunchedEffect(key1 = animatedRotation) {
        if (animatedRotation >= 360f) {
            viewModel.favorite(country)
            rotationState.floatValue = 0f
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = { onClick(index); viewModel.incrementTap() }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = country.flags.png),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Country: ${country.commonName}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Capital: ${country.capital?.joinToString() ?: "No Capital"}")
        }
        Icon(
            painter = if (country.isFavorite) favoriteFilledIcon else favoriteOutlineIcon,
            contentDescription = "Favorite Icon",
            modifier = Modifier
                .size(iconSize)
                .let { if (!country.isFavorite) it.rotate(animatedRotation) else it }  // Rotate only if it's not a favorite
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    rotationState.floatValue = 360f
                }
        )
    }
}

@Composable
fun CountryInfoRowShimmer() {
    val colors = listOf(
        Color.Gray.copy(alpha = 0.3f),
        Color.Gray.copy(alpha = 0.7f),
        Color.Gray.copy(alpha = 0.3f)
    )
    val transition = rememberInfiniteTransition()
    val animation = transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            tween(1500, easing = LinearEasing),
            RepeatMode.Restart
        )
    )

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val offsetDp = (animation.value * screenWidth).dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Brush.horizontalGradient(colors))
                .offset(x = offsetDp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth(0.7f)
                    .background(Brush.horizontalGradient(colors))
                    .offset(x = offsetDp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.5f)
                    .background(Brush.horizontalGradient(colors))
                    .offset(x = offsetDp)
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Brush.horizontalGradient(colors))
                .offset(x = offsetDp)
        )
    }
}