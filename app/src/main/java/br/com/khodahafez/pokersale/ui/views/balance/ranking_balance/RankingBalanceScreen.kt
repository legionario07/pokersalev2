package br.com.khodahafez.pokersale.ui.views.balance.ranking_balance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.pokersale.ui.ui.theme.mediumDimens
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.balance.BalanceStateUI
import br.com.khodahafez.pokersale.ui.views.balance.BalanceViewModel
import br.com.khodahafez.pokersale.ui.views.balance.BalanceViewModelFactory
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading

@Composable
fun RankingBalanceScreen(
    viewModel: RankingBalanceViewModel = viewModel(factory = RankingBalanceViewModelFactory()),
) {

    val stateUI by viewModel.stateUI.collectAsState()
    val context = LocalContext.current

    var loading by remember {
        mutableStateOf(false)
    }

    when (val result = stateUI) {
        is RankingBalanceStateUI.Initial -> {
            // Do Noting
        }

        is RankingBalanceStateUI.GetAllBalances -> {
            loading = false
//            MyBalanceContentScreen(result.expenseUiHelper)
        }

        is RankingBalanceStateUI.Loading -> {
            loading = true
        }

        is RankingBalanceStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }
    }

    CircularLoading(isLoading = loading)
}

@Composable
private fun RankingBalanceContentScreen() {

}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun CardContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(mediumDimens.size70)
            .padding(mediumDimens.size06),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFCCCCFF)
        ),
        shape = RoundedCornerShape(mediumDimens.size06),
        elevation = CardDefaults.cardElevation(mediumDimens.size04)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .padding(start = mediumDimens.size12),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
//                    text = playersHelpers.indexOf(it).inc().toString(),
                    text = "1",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
//                    text = it.player.name,
                    text = "João",
                    modifier = Modifier
                        .padding(bottom = mediumDimens.size02)
                        .align(Alignment.Start),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = mediumDimens.size02)
                        .align(Alignment.Start),
                    text = "Saldo: R$ -500,00",
//                    text = "${it.pointsTotal} gastos",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(end = mediumDimens.size30),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {

                Text(
//                    text = "${it.bounties} ganhos",
                    text = "R$ 1000,00 ganhos",
                    Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF869933)
                )

                Text(
//                    text = "${it.bounties} ganhos",
                    text = "R$ 1500,00 gastos",
                    Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}