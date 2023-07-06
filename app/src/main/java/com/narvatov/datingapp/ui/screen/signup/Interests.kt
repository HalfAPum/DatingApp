package com.narvatov.datingapp.ui.screen.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.button.WideButton
import com.narvatov.datingapp.ui.common.button.WideButtonSecondary
import com.narvatov.datingapp.ui.noRippleClickable
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.theme.TextPrimaryColor
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.signup.SignUpViewModel

@Composable
fun Interests(viewModel: SignUpViewModel) {
    val interestList by viewModel.interestsStateFlow.collectAsState()
    val selectedInterests = interestList.filter { it.selected }

    val interestsChooseAction = {
        viewModel.interestsSelected(selectedInterests)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = INTERESTS_GRID_SIZE),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item(span = { GridItemSpan(INTERESTS_GRID_SIZE) }) {
            Text(
                text = stringResource(R.string.your_interests),
                style = Typography.h4,
                modifier = Modifier.padding(top = 100.dp).padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(INTERESTS_GRID_SIZE) }) {
            Text(
                text = stringResource(R.string.select_at_least_3_interests),
                style = Typography.body2,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp).padding(horizontal = 16.dp),
            )

        }

        itemsIndexed(interestList) { index, interest ->
            val borderColor by animateColorAsState(if (interest.selected) PrimaryColor
                else BorderColor
            )

            val backgroundColor by animateColorAsState(if (interest.selected) PrimaryColor
                else Color.White
            )

            val iconColor by animateColorAsState(if (interest.selected) Color.White
                else PrimaryColor
            )

            val textColor by animateColorAsState(if (interest.selected) OnPrimaryColor
                else TextPrimaryColor
            )

            val paddingModifier = if (index % INTERESTS_GRID_SIZE == 0) Modifier.padding(start = 30.dp)
                else Modifier.padding(end = 16.dp)

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .then(paddingModifier)
                    .height(45.dp)
                    .clip(Shapes.small)
                    .background(color = backgroundColor, shape = Shapes.small)
                    .noRippleClickable { viewModel.updateInterestSelection(interest) }
                    .border(width = 1.dp, color = borderColor, shape = Shapes.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(interest.icon),
                    contentDescription = stringResource(R.string.interest_icon),
                    modifier = Modifier.padding(start = 12.dp).size(20.dp),
                    colorFilter = ColorFilter.tint(color = iconColor),
                )

                Text(
                    text = stringResource(interest.text),
                    style = Typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 6.dp),
                    color = textColor,
                )
            }
        }

        item(span = { GridItemSpan(INTERESTS_GRID_SIZE) }) {
            Box {
                AnimatedVisibility(
                    selectedInterests.count() >= REQUIRED_INTEREST_COUNT,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    WideButton(
                        text = stringResource(R.string.continue_t),
                        modifier = Modifier.padding(vertical = 50.dp).padding(horizontal = 16.dp),
                    ) { interestsChooseAction.invoke() }
                }
                AnimatedVisibility(
                    selectedInterests.count() < REQUIRED_INTEREST_COUNT,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    WideButtonSecondary(
                        text = stringResource(R.string.continue_t),
                        modifier = Modifier.padding(vertical = 50.dp).padding(horizontal = 16.dp),
                        onClick = {}
                    )
                }
            }
        }
    }
}

private const val INTERESTS_GRID_SIZE = 2
private const val REQUIRED_INTEREST_COUNT = 3