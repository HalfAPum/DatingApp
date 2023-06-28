package com.narvatov.datingapp.ui.screen.connect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment.Companion.Unbounded
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.SwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.common.square.SquareReportButton
import com.narvatov.datingapp.ui.navigation.FriendProfile
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.noRippleClickable
import com.narvatov.datingapp.ui.theme.GreenActive
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.connect.ConnectViewModel

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
                    Image(
                        bitmap = friend.photoBitmap,
                        contentScale = ContentScale.Crop,
                        contentDescription = friend.photoDescription(),
                        modifier = Modifier.fillMaxSize().noRippleClickable {
                            navigate(FriendProfile, friend)
                        },
                    )

                    SquareReportButton(
                        friend = friend,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopEnd)
                    )

                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (infoBackground, friendInfo, actionsRow) = createRefs()

                        Row(modifier = Modifier.constrainAs(actionsRow) {
                                bottom.linkTo(friendInfo.top)
                            }
                            .padding(20.dp)
                        ) {
                            WeightedSpacer()

                            ConnectActionButton(
                                imageVector = Icons.Rounded.ThumbDown,
                                backgroundColor = Color(0xFFE94057),
                            ) {
                                state.swipe(Direction.Left)
                            }

                            Spacer(modifier = Modifier.size(width = 40.dp, height = 1.dp))

                            ConnectActionButton(
                                imageVector = Icons.Rounded.ThumbUp,
                                backgroundColor = GreenActive,
                            ) {
                                state.swipe(Direction.Right)
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
                                bottom.linkTo(parent.bottom)
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