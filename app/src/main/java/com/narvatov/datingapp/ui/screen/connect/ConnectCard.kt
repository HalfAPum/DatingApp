package com.narvatov.datingapp.ui.screen.connect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment.Companion.Unbounded
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.SwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.narvatov.datingapp.R
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.common.SquareIconButton
import com.narvatov.datingapp.ui.navigation.FriendProfile
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.connect.ConnectViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun ConnectCard(
    friend: User,
    state: SwipeableCardState,
    viewModel: ConnectViewModel,
) {
    if (state.swipedDirection == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .swipableCard(
                    state = state,
                    onSwiped = { direction ->
                        when (direction) {
                                Direction.Left -> viewModel.rejectFriend(friend)
                            Direction.Right -> viewModel.acceptFriend(friend)
                            else -> {}
                        }
                    },
                    onSwipeCancel = {}
                )
        ) {
            Card(
                modifier = Modifier.clip(Shapes.large)
            ) {
                Box {
                    val interactionSource = remember { MutableInteractionSource() }
                    Image(
                        bitmap = friend.photoBitmap,
                        contentScale = ContentScale.Crop,
                        contentDescription = friend.photoDescription(),
                        modifier = Modifier.fillMaxSize().clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) {
                            navigate(FriendProfile, friend)
                        },
                    )

                    SquareIconButton(
                        iconRes = R.drawable.more,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(16.dp)
                            .align(Alignment.TopEnd)
                    ) {

                    }

                    val swipeScope = rememberCoroutineScope()
                    ConstraintLayout(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
                        val (infoBackground, friendInfo, actionsRow) = createRefs()

                        Row(modifier = Modifier.constrainAs(actionsRow) {
                                top.linkTo(parent.top)
                            }
                            .padding(20.dp)
                        ) {
                            WeightedSpacer()

                            Button(
                                colors = buttonColors(backgroundColor = Color(0xFFE94057)),
                                modifier = Modifier.size(width = 120.dp, height = 40.dp),
                                onClick = {
                                    swipeScope.launch {
                                        state.swipe(Direction.Left)
                                    }
                                }
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Image(
                                        imageVector = Icons.Rounded.ThumbDown,
                                        contentDescription = stringResource(R.string.dislike_person),
                                        modifier = Modifier.align(Alignment.Center),
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.size(width = 40.dp, height = 1.dp))

                            Button(
                                colors = buttonColors(backgroundColor = Color(0xFF17B53A)),
                                modifier = Modifier.size(width = 120.dp, height = 40.dp),
                                onClick = {
                                    swipeScope.launch {
                                        state.swipe(Direction.Right)
                                    }
                                }
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Image(
                                        imageVector = Icons.Rounded.ThumbUp,
                                        contentDescription = stringResource(R.string.like_person),
                                        modifier = Modifier.align(Alignment.Center),
                                        colorFilter = ColorFilter.tint(color = Color.White)
                                    )
                                }
                            }

                            WeightedSpacer()
                        }

                        var contentHeight by remember { mutableStateOf(0) }
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .blur(radius = 2.dp, Unbounded)
                            .constrainAs(infoBackground) {
                                top.linkTo(friendInfo.top)
                            }
                        ) {
                            Box(modifier = Modifier.then(
                                with(LocalDensity.current) {
                                    Modifier.height(
                                        height = contentHeight.toDp(),
                                    )
                                })
                                .fillMaxWidth()
                                .background(color = Color(0x80000000))
                            )
                        }

                        Column(modifier = Modifier.constrainAs(friendInfo) {
                                top.linkTo(actionsRow.bottom)
                            }.onSizeChanged {
                                contentHeight = it.height
                            }
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp, bottom = 16.dp)
                        ) {
                            Text(
                                text = friend.name,
                                style = Typography.h6,
                                color = OnPrimaryColor,
                            )
                            Text(
                                text = friend.name,
                                style = Typography.h6,
                                color = OnPrimaryColor,
                            )
                            Text(
                                text = friend.name,
                                style = Typography.h6,
                                color = OnPrimaryColor,
                            )
                        }
                    }
                }
            }
        }
    }
}