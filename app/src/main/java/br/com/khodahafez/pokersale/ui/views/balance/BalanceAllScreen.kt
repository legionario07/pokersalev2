@file:OptIn(ExperimentalFoundationApi::class)

package br.com.khodahafez.pokersale.ui.views.balance

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.khodahafez.domain.extensions.toMonetary
import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.pokersale.ui.ui.theme.mediumDimens
import br.com.khodahafez.pokersale.ui.utils.showToast
import br.com.khodahafez.pokersale.ui.views.components.CircularLoading

@Composable
fun BalanceAllScreen(
    viewModel: BalanceAllViewModel = viewModel(factory = BalanceAllViewModelFactory()),
) {
    val stateUI by viewModel.stateUI.collectAsState()
    val context = LocalContext.current

    var loading by remember {
        mutableStateOf(false)
    }

    when (val result = stateUI) {
        is BalancePokerStateUI.Initial -> {
            // Do Noting
        }

        is BalancePokerStateUI.GetAllBalances -> {
            BalanceAllContentScreen(199.0, balances = result.listBalances)
            loading = false
        }

        is BalancePokerStateUI.Loading -> {
            loading = true
        }

        is BalancePokerStateUI.Error -> {
            loading = false
            context.showToast(result.message)
        }
    }

    CircularLoading(isLoading = loading)
}

@ExperimentalFoundationApi
@Composable
private fun BalanceAllContentScreen(profit: Double, balances: List<Balance>) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 16.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Caixa: ${profit.toMonetary()}",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            items(balances, key = {
                it.id.orEmpty()
            }) { balance ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(mediumDimens.size06),
                    shape = RoundedCornerShape(mediumDimens.size06),
                    elevation = CardDefaults.cardElevation(mediumDimens.size04)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = balance.operationType?.operationType.orEmpty()
                        )

                        Text(
                            text = balance.date.orEmpty()
                        )

                        Text(
                            text = balance.value.toMonetary(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}