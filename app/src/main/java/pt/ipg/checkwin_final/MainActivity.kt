package pt.ipg.checkwin_final




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
                    DerrotaLayout(
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
            text = stringResource(R.string.quantidadeVitorias),
            modifier = Modifier.weight(1.0F)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

    }
}

@Composable
fun DerrotaLayout(modifier: Modifier = Modifier) {
    var quantidadeJogosInput by remember {
        mutableStateOf("")
    }

    var percentagemDerrotasageInput by remember {
        mutableStateOf("0")
    }

    var roundUpTip by remember {
        mutableStateOf(false)
    }

    val quantidadeJogos = quantidadeJogosInput.toDoubleOrNull() ?: 0.0
    val percentagemDerrotas = percentagemDerrotasageInput.toDoubleOrNull() ?: 0.0

    val derrotas = CalculaDerrotas(quantidadeJogos.toInt(), percentagemDerrotas.toInt(), roundUpTip)


    Box(
        modifier = modifier
            .fillMaxSize()
    )
    {
        Image(painter = painterResource(id = R.drawable.fundo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize())
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
                text = stringResource(R.string.inserirJogos),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            EditNumberField(
                labelText = stringResource(id = R.string.bill_quantidadeJogos),
                value = quantidadeJogosInput,
                onValueChange = { newValue -> quantidadeJogosInput = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Text(
                text = stringResource(R.string.inserirDerrotas),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            EditNumberField(
                labelText = stringResource(R.string.bill_quantidadeDerrotas),
                value = percentagemDerrotasageInput,
                onValueChange = { newValue -> percentagemDerrotasageInput = newValue },
                action = ImeAction.Done,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            RoundUpTipRow(
                checked = roundUpTip,
                onCheckedChange = { newValue -> roundUpTip = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.quantidadeDerrotas, derrotas),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipTimePreview() {
    CheckWin_FinalTheme {
        DerrotaLayout(
            modifier = Modifier.fillMaxSize()
        )
    }
}

private fun CalculaDerrotas(
    quantidadeJogos: Int,
    percentagemDerrotas: Int = 0,
    roundUpTip: Boolean = false
): String {
    var derrotas = (percentagemDerrotas / 100) * quantidadeJogos

    if (roundUpTip) {
        derrotas = kotlin.math.ceil(derrotas.toDouble()).toInt()
    }

    return NumberFormat.getCurrencyInstance().format(derrotas)
}