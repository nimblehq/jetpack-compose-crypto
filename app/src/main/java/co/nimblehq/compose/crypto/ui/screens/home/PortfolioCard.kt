package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.common.price.PriceChangeButton
import co.nimblehq.compose.crypto.ui.theme.*

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
                    colors = AppTheme.colors.portfolioCardBackgroundGradient,
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
            style = AppTheme.styles.lightSilverMedium16
        )

        Text(
            modifier = Modifier
                .constrainAs(totalCoins) {
                    top.linkTo(totalCoinsLabel.bottom, margin = Dp8)
                },
            // TODO: Remove dummy value when work on Integrate.
            text = stringResource(R.string.coin_currency, "7,273,291"),
            style = AppTheme.styles.whiteSemiBold24
        )

        Text(
            modifier = Modifier
                .constrainAs(todayProfitLabel) {
                    top.linkTo(totalCoins.bottom, margin = Dp40)
                },
            text = stringResource(R.string.portfolio_card_today_profit_label),
            style = AppTheme.styles.lightSilverMedium16
        )

        Text(
            modifier = Modifier
                .constrainAs(todayProfit) {
                    top.linkTo(todayProfitLabel.bottom, margin = Dp8)
                },
            // TODO: Remove dummy value when work on Integrate.
            text = stringResource(R.string.coin_currency, "193,280"),
            style = AppTheme.styles.whiteSemiBold24
        )

        PriceChangeButton(
            modifier = Modifier
                .constrainAs(profitPercent) {
                    linkTo(top = todayProfitLabel.top, bottom = todayProfit.bottom)
                    end.linkTo(parent.end)
                },
            // TODO: Remove dummy value when work on Integrate.
            priceChangePercent = 6.21,
        )
    }
}

@Composable
@Preview
private fun PortfolioCardPreview() {
    ComposeTheme {
        PortfolioCard(
            modifier = Modifier
        )
    }
}
