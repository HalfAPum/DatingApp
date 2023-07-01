package com.narvatov.datingapp.ui.screen.filter.child

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.HintGrey
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.TextPrimaryColor
import com.narvatov.datingapp.ui.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AgeFilter(
    ageRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    onAgeRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(start = thumbRadius)) {
            Text(
                text = stringResource(R.string.age),
                style = Typography.body1,
                color = TextPrimaryColor,
                fontWeight = FontWeight.Bold,
            )

            WeightedSpacer()

            val endValue = if (ageRange.endInclusive == AGE_RANGE_END) "${AGE_RANGE_END.toInt()}+"
            else ageRange.endInclusive.toInt().toString()

            Text(
                text = "${ageRange.start.toInt()}-$endValue",
                style = Typography.body2,
                color = HintGrey,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        val stepsRange = AGE_RANGE_START..AGE_RANGE_END

        val stepsCount = (stepsRange.endInclusive - stepsRange.start - 1).toInt()

        RangeSlider(
            values = ageRange,
            onValueChange = { sliderValuesChanged ->
                when {
                    sliderValuesChanged.start <= AGE_RANGE_START -> {
                        onAgeRangeChanged.invoke(AGE_RANGE_START..sliderValuesChanged.endInclusive)
                    }
                    sliderValuesChanged.endInclusive <= sliderValuesChanged.start -> {
                        onAgeRangeChanged.invoke(sliderValuesChanged.start..sliderValuesChanged.start)
                    }
                    else -> {
                        onAgeRangeChanged.invoke(sliderValuesChanged)
                    }
                }
            },
            valueRange = stepsRange,
            onValueChangeFinished = {},
            steps = stepsCount,
            colors = SliderDefaults.colors(
                activeTickColor = PrimaryColor,
                inactiveTickColor = BorderColor,
                inactiveTrackColor = BorderColor
            ),
        )
    }
}

private val thumbRadius = 10.dp

const val AGE_RANGE_START = 18F
private const val AGE_RANGE_END = 99F
const val AGE_RANGE_OPTIMAL_MAX = 60F