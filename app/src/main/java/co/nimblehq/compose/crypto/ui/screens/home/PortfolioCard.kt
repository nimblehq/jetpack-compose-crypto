package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.Color
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp0
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp12
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp13
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp20
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun PortfolioCard(
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dp12))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.MetallicSeaweed, Color.TiffanyBlue),
                )
            )
            .padding(horizontal = Dp16, vertical = Dp16)
    ) {
        val (
            totalCoinsLabel,
            totalCoins,
            todayProfitLabel,
            todayProfit,
            profitPercent
        ) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(totalCoinsLabel) {
                    start.linkTo(parent.start)
                },
            text = stringResource(R.string.portfolio_card_total_coin_label),
            style = Style.lightSilverMedium16()
        )

        Text(
            modifier = Modifier
                .constrainAs(totalCoins) {
                    top.linkTo(totalCoinsLabel.bottom, margin = Dp8)
                },
            // TODO: Remove dummy value when work on Integrate.
            text = stringResource(R.string.coin_currency, "7,273,291"),
            style = Style.whiteSemiBold24()
        )

        Text(
            modifier = Modifier
                .constrainAs(todayProfitLabel) {
                    top.linkTo(totalCoins.bottom, margin = Dp40)
                },
            text = stringResource(R.string.portfolio_card_today_profit_label),
            style = Style.lightSilverMedium16()
        )

        Text(
            modifier = Modifier
                .constrainAs(todayProfit) {
                    top.linkTo(todayProfitLabel.bottom, margin = Dp8)
                },
            // TODO: Remove dummy value when work on Integrate.
            text = stringResource(R.string.coin_currency, "193,280"),
            style = Style.whiteSemiBold24()
        )

        Button(
            modifier = Modifier
                .shadow(elevation = Dp0)
                .constrainAs(profitPercent) {
                    linkTo(top = todayProfitLabel.top, bottom = todayProfit.bottom)
                    end.linkTo(parent.end)
                },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Water,
                contentColor = Color.GuppieGreen
            ),
            shape = RoundedCornerShape(Dp20),
            contentPadding = PaddingValues(start = Dp13, end = Dp8),
            onClick = { /* TODO */ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_guppie_green_arrow_up),
                modifier = Modifier.padding(end = Dp13),
                contentDescription = null
            )
            Text(
                // TODO: Remove dummy value when work on Integrate.
                text = stringResource(R.string.coin_profit_percent, "2.41"),
                style = Style.medium16()
            )
        }
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun PortfolioCardPreview() {
    ComposeTheme {
        PortfolioCard(
            modifier = Modifier
        )
    }
}
