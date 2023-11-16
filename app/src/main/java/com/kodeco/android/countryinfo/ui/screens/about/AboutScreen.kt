package com.kodeco.android.countryinfo.ui.screens.about

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kodeco.android.countryinfo.BuildConfig
import com.kodeco.android.countryinfo.R
import kotlinx.coroutines.delay

interface MockableNavController {
    fun popBackStack()
    // Add other methods you use from NavController if needed
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutScreen(navController: MockableNavController) {
    var visible by remember { mutableStateOf(false) }
    var navigateBack by remember { mutableStateOf(false) }

    LaunchedEffect(navigateBack) {
        if (navigateBack) {
            delay(400)
            navController.popBackStack()
        }
    }

    // Handle delayed entry animation
    LaunchedEffect(Unit) {
        delay(400)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(400)),
        exit = fadeOut(animationSpec = tween(400))
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.about_screen)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            visible = false  // Trigger exit animation
                            navigateBack = true
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(id = R.string.app_info),
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center).padding(horizontal = 8.dp)
                )
                Text(
                    text = "Version: ${BuildConfig.VERSION_NAME}",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    val mockNavController = object : MockableNavController {
        override fun popBackStack() {
        }
    }

    AboutScreen(navController = mockNavController)
}

class NavControllerAdapter(private val navController: NavController) : MockableNavController {
    override fun popBackStack() {
        navController.popBackStack()
    }
}