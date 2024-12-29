package br.com.khodahafez.pokersale.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun YearSelector(
    modifier: Modifier = Modifier,
    onYearSelected: (Int) -> Unit
) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = listOf(
        currentYear - 1, currentYear
    )

    LaunchedEffect(Unit) {
        onYearSelected(currentYear)
    }

    var selectedYear by remember {
        mutableStateOf(years.last())
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(
                vertical = 24.dp,
                horizontal = 32.dp
            )
    ) {
        Text(
            modifier =  Modifier.fillMaxWidth(),
            text = "Selecione o ano:",
            style = TextStyle(fontSize = 18.sp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuSample(
            years = years,
            selectedYear = selectedYear,
        ) { year ->
            selectedYear = year
            onYearSelected(year)
        }
    }
}

@Composable
fun DropdownMenuSample(
    years: List<Int>,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(
                vertical = 24.dp,
                horizontal = 32.dp
            ),
    ) {
       OutlinedButton(
           modifier = Modifier.fillMaxWidth(),
           border = BorderStroke(
               width = 2.dp,
               Color(0xFF466583)
           ),
           onClick = {
               expanded = !expanded
           }
       ) {
           Text(
               modifier = Modifier.fillMaxWidth(),
               text = selectedYear.toString(),
               textAlign = TextAlign.Center
           )
       }

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            years.forEach {year ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = year.toString(),
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun YearSelectorPreview() {
    YearSelector {

    }
}