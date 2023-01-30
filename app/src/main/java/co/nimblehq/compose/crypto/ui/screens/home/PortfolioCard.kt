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

@Composable
fun PortfolioCard(
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(co.nimblehq.compose.crypto.core.theme.Dp12))
            .background(
                brush = Brush.linearGradient(
                    colors = co.nimblehq.compose.crypto.core.theme.AppTheme.colors.portfolioCardBackgroundGradient,
                )
            )
            .padding(horizontal = co.nimblehq.compose.crypto.core.theme.Dp16, vertical = co.nimblehq.compose.crypto.core.theme.Dp16)
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
            style = co.nimblehq.compose.crypto.core.theme.AppTheme.styles.lightSilverMedium16
        )

        Text(
            modifier = Modifier
                .constrainAs(totalCoins) {
                    top.linkTo(totalCoinsLabel.bottom, margin = co.nimblehq.compose.crypto.core.theme.Dp8)
                },
            // TODO: Remove dummy value when work on Integrate.
            text = stringResource(R.string.coin_currency, "7,273,291"),
            style = co.nimblehq.compose.crypto.core.theme.AppTheme.styles.whiteSemiBold24
        )

        Text(
            modifier = Modifier
                .constrainAs(todayProfitLabel) {
                    top.linkTo(totalCoins.bottom, margin = co.nimblehq.compose.crypto.core.theme.Dp40)
                },
            text = stringResource(R.string.portfolio_card_today_profit_label),
            style = co.nimblehq.compose.crypto.core.theme.AppTheme.styles.lightSilverMedium16
        )

        Text(
            modifier = Modifier
                .constrainAs(todayProfit) {
                    top.linkTo(todayProfitLabel.bottom, margin = co.nimblehq.compose.crypto.core.theme.Dp8)
                },
            // TODO: Remove dummy value when work on Integrate.
            text = stringResource(R.string.coin_currency, "193,280"),
            style = co.nimblehq.compose.crypto.core.theme.AppTheme.styles.whiteSemiBold24
        )

        co.nimblehq.compose.crypto.core.common.price.PriceChangeButton(
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
fun PortfolioCardPreview() {
    co.nimblehq.compose.crypto.core.theme.ComposeTheme {
        PortfolioCard(
            modifier = Modifier
        )
    }
}
