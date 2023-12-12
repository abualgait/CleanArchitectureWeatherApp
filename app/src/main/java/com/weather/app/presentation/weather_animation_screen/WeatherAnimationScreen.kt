package com.weather.app.presentation.weather_animation_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.weather.app.R
import com.weather.app.theme.Blue400
import com.weather.app.theme.Grey1
import kotlinx.coroutines.delay
import kotlin.random.Random

val IMAGE_SIZE = 60.dp
val IMAGE_PADDING = 7.dp
val INNER_IMAGE_PADDING = 5.dp
val ROUNDED_CORNER = 10.dp


@Composable
fun AnimationScreen() {
    var animateRainy by remember { mutableStateOf(false) }
    var animateSunny by remember { mutableStateOf(false) }
    var animateSunnyAndCloudy by remember { mutableStateOf(false) }
    var animateNightAndCloudy by remember { mutableStateOf(false) }
    var animateNight by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        if (animateSunny) {
            SunnyAnimation(Modifier.fillMaxSize())
        }

        if (animateNight) {
            NightAnimation(Modifier.fillMaxSize())
        }

        if (animateRainy) {
            RainyAnimation(Modifier.fillMaxSize())
        }

        if (animateSunnyAndCloudy) {
            SunnyAnimation(Modifier.fillMaxSize())
            CloudyAnimation(Modifier.fillMaxSize())
        }

        if (animateNightAndCloudy) {
            NightAnimation(Modifier.fillMaxSize())
            CloudyAnimation(Modifier.fillMaxSize())
        }



        Card(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(IMAGE_PADDING),
            shape = RoundedCornerShape(
                ROUNDED_CORNER
            )
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .background(Grey1),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painterResource(R.drawable.rainy),
                    contentDescription = "",
                    modifier = Modifier
                        .roundImage()
                        .background(if (animateRainy) Blue400 else Color.Transparent)

                        .toggleable(
                            value = animateRainy,
                            onValueChange = {
                                animateRainy = !animateRainy
                            }
                        )
                        .padding(INNER_IMAGE_PADDING)
                )
                Image(
                    painterResource(R.drawable.sunny),
                    contentDescription = "",
                    modifier = Modifier
                        .roundImage()
                        .background(if (animateSunny) Blue400 else Color.Transparent)

                        .toggleable(
                            value = animateSunny,
                            onValueChange = {
                                animateNight = false
                                animateSunny = !animateSunny
                            }
                        )
                        .padding(INNER_IMAGE_PADDING)
                )
                Image(
                    painterResource(R.drawable.sunny_night),
                    contentDescription = "",
                    modifier = Modifier
                        .roundImage()
                        .background(if (animateNight) Blue400 else Color.Transparent)

                        .toggleable(
                            value = animateNight,
                            onValueChange = {
                                animateSunny = false
                                animateRainy = false
                                animateNight = !animateNight
                            }
                        )
                        .padding(INNER_IMAGE_PADDING)
                )
                Image(
                    painterResource(R.drawable.cloudy),
                    contentDescription = "",
                    modifier = Modifier
                        .roundImage()
                        .background(if (animateSunnyAndCloudy) Blue400 else Color.Transparent)

                        .toggleable(
                            value = animateSunnyAndCloudy,
                            onValueChange = {
                                animateRainy = false
                                animateNight = false
                                animateNightAndCloudy = false
                                animateSunny = false
                                animateSunnyAndCloudy = !animateSunnyAndCloudy
                            }
                        )
                        .padding(INNER_IMAGE_PADDING)
                )


                Image(
                    painterResource(R.drawable.cloudy_night),
                    contentDescription = "",
                    modifier = Modifier
                        .roundImage()
                        .background(if (animateNightAndCloudy) Blue400 else Color.Transparent)
                        .toggleable(
                            value = animateNightAndCloudy,
                            onValueChange = {
                                animateRainy = false
                                animateNight = false
                                animateSunnyAndCloudy = false
                                animateSunny = false
                                animateNightAndCloudy = !animateNightAndCloudy
                            }
                        )
                        .padding(INNER_IMAGE_PADDING)
                )
            }
        }


    }
}

@Composable
fun RainyAnimation(modifier: Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val randomXYPositions =
        generateRandomPositions(10, screenWidth.value.toInt(), (screenHeight.value * 0.20f).toInt())

    Box(modifier) {

        randomXYPositions.forEach {
            AnimatedRainy(position = it)
        }
    }

}

@Composable
fun CloudyAnimation(modifier: Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val randomXYPositions =
        generateRandomPositions(3, screenWidth.value.toInt(), (screenHeight.value * 0.20f).toInt())

    Box(modifier) {
        randomXYPositions.forEach {
            AnimatedCloudy(position = it)
        }
    }

}

@Composable
fun SunnyAnimation(modifier: Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var forwardAnimation by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        delay(100)
        forwardAnimation = !forwardAnimation
    }
    Box(
        modifier.background(
            brush = Brush.verticalGradient(
                listOf(
                    Color(0xFFFFEB3B),
                    Color(0xFFFFFFFF),
                )
            )
        )
    ) {
        val animatedOffset by animateIntOffsetAsState(
            animationSpec = tween(durationMillis = 1000),
            targetValue = if (forwardAnimation) IntOffset.Zero else IntOffset(
                70,
                70
            ),
            finishedListener = {

            },
            label = ""
        )
        Image(
            painterResource(R.drawable.sunny),
            contentDescription = "",
            modifier = Modifier
                .offset(animatedOffset.x.dp, animatedOffset.y.dp)
                .scale(1.5f)

        )
    }

}

@Composable
fun NightAnimation(modifier: Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var forwardAnimation by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        delay(100)
        forwardAnimation = !forwardAnimation
    }
    Box(
        modifier.background(
            brush = Brush.verticalGradient(
                listOf(
                    Color(0xBA191970),
                    Color(0xE8000033)
                )
            )
        )
    ) {
        val animatedOffset by animateIntOffsetAsState(
            animationSpec = tween(durationMillis = 1000),
            targetValue = if (forwardAnimation) IntOffset.Zero else IntOffset(
                70,
                70
            ),
            finishedListener = {

            },
            label = ""
        )
        Image(
            painterResource(R.drawable.sunny_night),
            contentDescription = "",
            modifier = Modifier
                .offset(animatedOffset.x.dp, animatedOffset.y.dp)
                .scale(1.5f)

        )
    }

}

@Composable
fun AnimatedRainy(position: Position) {
    var forwardAnimation by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        delay(100)
        forwardAnimation = !forwardAnimation
    }
    val animatedOffset by animateIntOffsetAsState(
        animationSpec = tween(durationMillis = 1000),
        targetValue = if (forwardAnimation) IntOffset(position.x, 0) else IntOffset(
            position.x,
            position.y
        ),
        finishedListener = {

        },
        label = ""
    )
    val animatedScale by animateFloatAsState(
        animationSpec = tween(durationMillis = 1000),
        targetValue = if (forwardAnimation) Random.nextDouble(1.0).toFloat() else Random.nextDouble(
            1.5
        ).toFloat(),
        label = ""
    )

    Image(
        painterResource(R.drawable.rainy),
        contentDescription = "",
        modifier = Modifier
            .offset(animatedOffset.x.dp, animatedOffset.y.dp)
            .scale(animatedScale)

    )
}


@Composable
fun AnimatedCloudy(position: Position) {
    var forwardAnimation by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        delay(100)
        forwardAnimation = !forwardAnimation
    }
    val animatedOffset by animateIntOffsetAsState(
        animationSpec = tween(durationMillis = 1000),
        targetValue = if (forwardAnimation) IntOffset(position.x, 0) else IntOffset(
            position.x,
            position.y
        ),
        finishedListener = {

        },
        label = ""
    )
    val animatedScale by animateFloatAsState(
        animationSpec = tween(durationMillis = 1000),
        targetValue = if (forwardAnimation) Random.nextDouble(1.0).toFloat() else Random.nextDouble(
            2.5
        ).toFloat(),
        label = ""
    )

    Image(
        painterResource(R.drawable.clouds),
        contentDescription = "",
        modifier = Modifier
            .offset(animatedOffset.x.dp, animatedOffset.y.dp)
            .scale(animatedScale)

    )
}

data class Position(val x: Int, val y: Int)

fun generateRandomPositions(numPositions: Int, width: Int, height: Int): List<Position> {
    val positions = mutableListOf<Position>()
    repeat(numPositions) {
        val x = Random.nextInt(0, width)
        val y = Random.nextInt(0, height)
        positions.add(Position(x, y))
    }
    return positions
}

fun Modifier.roundImage(): Modifier {
    return size(IMAGE_SIZE)
        .padding(IMAGE_PADDING)
        .clip(RoundedCornerShape(ROUNDED_CORNER))
}