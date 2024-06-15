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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pt.ipg.checkwin_final.ui.theme.CheckWin_FinalTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import kotlin.math.ceil


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            CheckWin_FinalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "derrotaLayout") {
                        composable("derrotaLayout") {
                            DerrotaLayout(navController = navController, modifier = Modifier.padding(innerPadding).fillMaxSize())
                        }
                        composable("newPage") {
                            NewPage(navController = navController)
                        }
                    }
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
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.quantidadeVitorias),
            color = Color.Red,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = { navController.navigate("newPage") }) {
            Text(text = "Ir para Nova Página")
        }
    }
}

fun CalculaDerrotas(
    quantidadeJogos: Int,
    percentagemDerrotas: Int = 0,
    roundUpTip: Boolean = false
): Int {
    var derrotas = (percentagemDerrotas / 100.0) * quantidadeJogos

    if (roundUpTip) {
        derrotas = ceil(derrotas)
    }

    return derrotas.toInt()
}

@Composable
fun DerrotaLayout(modifier: Modifier = Modifier, navController: NavHostController) {
    var quantidadeJogosInput by remember {
        mutableStateOf("") }

    var percentagemDerrotasInput by remember {
        mutableStateOf("0")
    }
    var roundUpTip by remember {
        mutableStateOf(false)
    }

    val quantidadeJogos = quantidadeJogosInput.toDoubleOrNull() ?: 0.0
    val percentagemDerrotas = percentagemDerrotasInput.toDoubleOrNull() ?: 0.0

    val derrotas = CalculaDerrotas(quantidadeJogos.toInt(), percentagemDerrotas.toInt(), roundUpTip)

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fundo),
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
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            EditNumberField(
                labelText = stringResource(R.string.bill_quantidadeDerrotas),
                value = percentagemDerrotasInput,
                onValueChange = { newValue -> percentagemDerrotasInput = newValue },
                action = ImeAction.Done,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            RoundUpTipRow(
                navController = navController,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.quantidadeDerrotas, derrotas),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Composable
fun TipTimePreviewDerrota() {
    CheckWin_FinalTheme {
        DerrotaLayout(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}

@Composable
fun NewPage(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Esta é a nova página")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewNewPage() {
    CheckWin_FinalTheme {
        NewPage(navController = rememberNavController())
    }
}
