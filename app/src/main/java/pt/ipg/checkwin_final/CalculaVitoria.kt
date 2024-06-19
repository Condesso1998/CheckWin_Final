package pt.ipg.checkwin_final

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.ipg.checkwin_final.ui.theme.CheckWin_FinalTheme
import kotlin.math.ceil

class NewPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheckWin_FinalTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DerrotaLayoutVitorias(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun EditNumberField_Vitorias(
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
fun RoundUpTipRowVitorias(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.mudaDerrota),
            color = Color.Yellow,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(onClick = {
            navController.navigate("Derrotas")
        }) {
            Text(text = "Mudar Para Derrotas")
        }
        // Uncomment if Switch is needed
        // Switch(
        //    checked = checked,
        //    onCheckedChange = onCheckedChange
        // )
    }
}

@Preview
@Composable
fun DerrotaLayoutVitorias(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    var quantidadeJogosInput by remember { mutableStateOf("") }
    var percentagemVitoriasageInput by remember { mutableStateOf("0") }
    var roundUpTip by remember { mutableStateOf(false) }

    val quantidadeJogos = quantidadeJogosInput.toDoubleOrNull() ?: 0.0
    val percentagemVitorias = percentagemVitoriasageInput.toDoubleOrNull() ?: 0.0

    val vitorias = CalculaVitorias(quantidadeJogos.toInt(), percentagemVitorias.toInt(), roundUpTip)

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
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
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            EditNumberField_Vitorias(
                labelText = stringResource(id = R.string.bill_quantidadeJogos),
                value = quantidadeJogosInput,
                onValueChange = { newValue -> quantidadeJogosInput = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Text(
                text = stringResource(R.string.inserirVitorias),
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            EditNumberField_Vitorias(
                labelText = stringResource(R.string.bill_quantidadeVitorias),
                value = percentagemVitoriasageInput,
                onValueChange = { newValue -> percentagemVitoriasageInput = newValue },
                action = ImeAction.Done,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            RoundUpTipRowVitorias(
                checked = roundUpTip,
                onCheckedChange = { newValue -> roundUpTip = newValue },
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.quantidadeVitorias, vitorias),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Composable
fun TipTimePreviewVitorias() {
    CheckWin_FinalTheme {
        DerrotaLayoutVitorias(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}

@SuppressLint("SuspiciousIndentation")
private fun CalculaVitorias(
    quantidadeJogos: Int,
    percentagemVitorias: Int = 0,
    roundUpTip: Boolean = false
): Int {
    var vitorias = (percentagemVitorias / 100.0) * quantidadeJogos

    if (roundUpTip) {
        vitorias = ceil(vitorias)
    }

    return vitorias.toInt()
}
