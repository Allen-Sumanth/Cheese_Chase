package com.example.cheesechase.screens

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cheesechase.GameViewModel
import com.example.cheesechase.R
import com.example.cheesechase.navigation.Screens
import com.example.cheesechase.ui.theme.ButtonFont
import com.example.cheesechase.ui.theme.HomePageBackground
import com.example.cheesechase.ui.theme.HomePageButtonBackground
import com.example.cheesechase.ui.theme.TitleColour
import com.example.cheesechase.ui.theme.jollyLodger

@Composable
fun HomePage(navController: NavController, viewModel: GameViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomePageBackground)
            .padding(25.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                //            modifier = Modifier.height(250.dp),
                text = "CHEESE",
                fontFamily = jollyLodger,
                fontSize = 100.sp,
                color = TitleColour,
                textAlign = TextAlign.Center,
            )
            Text(
                //            modifier = Modifier.height(250.dp),
                text = "CHASE",
                fontFamily = jollyLodger,
                fontSize = 100.sp,
                color = TitleColour,
                textAlign = TextAlign.Center,
            )
        }

        val cheeseTransition = rememberInfiniteTransition(label = "")
        val cheeseHeight = cheeseTransition.animateFloat(
            initialValue = -20f,
            targetValue = 20f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "cheese height"
        )

        Image(
            painter = painterResource(R.drawable.cheese_icon),
            contentDescription = "Cheese Icon",
            modifier = Modifier
                .rotate(-14f)
                .size(190.dp)
                .offset(y = cheeseHeight.value.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = CircleShape,
                    modifier = Modifier.size(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HomePageButtonBackground
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                    border = BorderStroke(width = 5.dp, color = TitleColour)
                ) {
                }
                Text(text = "?", fontSize = 35.sp, color = ButtonFont, fontWeight = FontWeight.W900)
            } //info button

            Button( //play button
                onClick = {
                    navController.navigate(Screens.GamePage.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomePageButtonBackground
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                border = BorderStroke(width = 5.dp, color = TitleColour)
            ) {
                Text(
                    text = "play",
                    fontSize = 50.sp,
                    fontFamily = jollyLodger,
                    color = ButtonFont,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }

            Box { //holds options
                var menuOpen by remember {
                    mutableStateOf(false)
                }
                val menuOpenTransition = updateTransition(targetState = menuOpen, label = "menu")
                val highScoresButtonOffset by menuOpenTransition.animateDp(label = "high score") { isMenuOpen ->
                    when(isMenuOpen){
                        true -> (-65).dp
                        false -> 0.dp
                    }
                }
                val audioButtonOffset by menuOpenTransition.animateDp(label = "audio") { isMenuOpen ->
                    when(isMenuOpen){
                        true -> (-130).dp
                        false -> 0.dp
                    }
                }

                //options button
                Box(contentAlignment = Alignment.Center, modifier = Modifier.zIndex(1f)) {
                    Button(
                        onClick = { menuOpen = !menuOpen },
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HomePageButtonBackground
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                        border = BorderStroke(width = 5.dp, color = TitleColour)
                    ) {

                    }
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = ButtonFont,
                        modifier = Modifier.size(30.dp)
                    )
                }
                //Highscores button
                Box(contentAlignment = Alignment.Center, modifier = Modifier.offset(y = highScoresButtonOffset)) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HomePageButtonBackground
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                        border = BorderStroke(width = 5.dp, color = TitleColour)
                    ) {

                    }
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Options",
                        tint = ButtonFont,
                        modifier = Modifier.size(30.dp)
                    )
                }
                //Audio toggle
                Box(contentAlignment = Alignment.Center, modifier = Modifier.offset(y = audioButtonOffset)) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = CircleShape,
                        modifier = Modifier.size(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HomePageButtonBackground
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                        border = BorderStroke(width = 5.dp, color = TitleColour)
                    ) {

                    }
                    Image(
                        painter = painterResource(id = R.drawable.audio_symbol),
                        contentDescription = "audio symbol",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun HomePagePreview(modifier: Modifier = Modifier) {
    HomePage(navController = rememberNavController(), viewModel = GameViewModel())
}