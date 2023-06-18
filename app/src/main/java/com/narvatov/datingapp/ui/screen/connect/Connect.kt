package com.narvatov.datingapp.ui.screen.connect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.connect.ConnectViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun Connect(
    viewModel: ConnectViewModel = getViewModel(),
) {
    val newFriends by viewModel.newFriendStateFlow.collectAsState()

    val states = newFriends.associateWith { rememberSwipeableCardState() }

    states.forEach { (friend, state) ->
        if (state.swipedDirection == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .swipableCard(
                        state = state,
                        onSwiped = { direction ->
                            when(direction) {
                                Direction.Left -> viewModel.rejectFriend(friend)
                                Direction.Right -> viewModel.acceptFriend(friend)
                                else -> {}
                            }
                        },
                        onSwipeCancel = {}
                    )
            ) {
                Card(modifier = Modifier
                    .padding(10.dp)
                    .clip(Shapes.large)
                    .border(1.dp, Color.Black, shape = Shapes.large)
                    .shadow(elevation = 10.dp, shape = Shapes.large)
                ) {
                    Box {
                        Image(
                            bitmap = friend.photoBitmap,
                            contentScale = ContentScale.Crop,
                            contentDescription = friend.photoDescription(),
                            modifier = Modifier.fillMaxSize(),
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(color = Color.White)
                                .align(Alignment.BottomCenter)
                        ) {
                            Text(
                                text = friend.name,
                                style = Typography.h6,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}