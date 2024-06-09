package pt.ipg.checkwin_final




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ipg.checkwin_final.ui.theme.CheckWin_FinalTheme
import java.text.NumberFormat
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheckWin_FinalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipTimeLayout(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun EditNumberField(
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    action: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = action
        ),
        modifier = modifier

    )

}

@Composable
fun RoundUpTipRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.round_up_tip),
            modifier = Modifier.weight(1.0F)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun TipTimeLayout(modifier: Modifier = Modifier) {
    var amountInput by remember {
        mutableStateOf("")
    }

    var tipPercentageInput by remember {
        mutableStateOf("")
    }

    var roundUpTip by remember {
        mutableStateOf(false)
    }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipPercentageInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(amount, tipPercent, roundUpTip)

    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.num_vitorias),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )

        EditNumberField(
            labelText = stringResource(id = R.string.bill_amount),
            value = amountInput,
            onValueChange = { newValue -> amountInput = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.num_derrotas),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )

        EditNumberField(
            labelText = stringResource(R.string.tip_percentage),
            value = tipPercentageInput,
            onValueChange = { newValue -> tipPercentageInput = newValue },
            action = ImeAction.Done,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )


        RoundUpTipRow(
            checked = roundUpTip,
            onCheckedChange = { newValue -> roundUpTip = newValue},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TipTimePreview() {
    CheckWin_FinalTheme {
        TipTimeLayout(
            modifier = Modifier.fillMaxSize()
        )
    }
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0,
    roundUpTip: Boolean = false
): String {
    var tip = (tipPercent / 100) * amount

    if (roundUpTip) {
        tip = kotlin.math.ceil(tip)
    }

    return NumberFormat.getCurrencyInstance().format(tip)
}
