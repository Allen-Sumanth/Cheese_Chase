package com.example.cheesechase.screens

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.cheesechase.component_classes.AudioClass
import com.example.cheesechase.component_classes.AudioType
import com.example.cheesechase.GameViewModel
import com.example.cheesechase.R
import com.example.cheesechase.navigation.Screens
import com.example.cheesechase.ui.theme.ButtonFont
import com.example.cheesechase.ui.theme.GameOverText
import com.example.cheesechase.ui.theme.HighScoreBackground
import com.example.cheesechase.ui.theme.HomePageBackground
import com.example.cheesechase.ui.theme.HomePageButtonBackground
import com.example.cheesechase.ui.theme.ScoreCardBackground
import com.example.cheesechase.ui.theme.TitleColour
import com.example.cheesechase.ui.theme.anonymousProBold
import com.example.cheesechase.ui.theme.jollyLodger

@Composable
fun HomePage(
    navController: NavController,
    viewModel: GameViewModel,
    audioMap: Map<AudioType, AudioClass>,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomePageBackground)
            .padding(25.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //title
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

        //cheese image
        Image(
            painter = painterResource(R.drawable.cheese_icon),
            contentDescription = "Cheese Icon",
            modifier = Modifier
                .rotate(-14f)
                .size(190.dp)
                .offset(y = cheeseHeight.value.dp)
        )

        //buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                Button(//info button
                    onClick = {
                        audioMap[AudioType.BUTTON]?.play(volume = 0.5f)
                    },
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
            }

            Button( //play button
                onClick = {
                    audioMap[AudioType.ENTER]?.play(volume = 1f)

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
            //holds options
            Box {
                //Highscores button
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.openHighScoreDialog = true
                            audioMap[AudioType.BUTTON]?.play(volume = 0.5f)
                        },
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

            }
        }

        //HighScore Dialog
        if (viewModel.openHighScoreDialog) {
            HighScoreDialog(
                viewModel = viewModel,
                audioMap = audioMap,
                highScore = 0
            )
        }
    }
}

@Composable
fun HighScoreDialog(viewModel: GameViewModel, audioMap: Map<AudioType, AudioClass>, highScore: Int) {
    Dialog(onDismissRequest = {
        viewModel.openHighScoreDialog = false
        audioMap[AudioType.BUTTON]?.play(1f)
    }) {
        Card(
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .size(300.dp, 200.dp)
                .padding(10.dp, 5.dp, 10.dp, 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = HighScoreBackground
            ),
            border = BorderStroke(width = 10.dp, color = Color.Black),
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "HIGH SCORE",
                    fontFamily = anonymousProBold,
                    fontSize = 35.sp,
                    color = GameOverText,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    //Score Card
                    Card(
                    shape = RoundedCornerShape(100),
                    colors = CardDefaults.cardColors(
                        containerColor = ScoreCardBackground
                    ),
                    border = BorderStroke(width = 6.dp, color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                    ) {
                        Text(
                            text = "${highScore}",
                            fontFamily = anonymousProBold,
                            fontSize = if (viewModel.gameScore > 100) 35.sp else 40.sp,
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 5.dp)
                                .align(alignment = Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Visible
                        )
                    }
                    //reset game button
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        Button(
                            onClick = {
                                audioMap[AudioType.BUTTON]?.play(1f)
                            },
                            shape = CircleShape,
                            modifier = Modifier.size(65.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HomePageButtonBackground
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                            border = BorderStroke(width = 6.dp, color = Color.Black)
                        ) {
                        }
                        Image(
                            painter = painterResource(id = R.drawable.reset_icon),
                            contentDescription = "pause symbol",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}