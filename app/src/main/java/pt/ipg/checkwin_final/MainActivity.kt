package pt.ipg.checkwin_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.ipg.checkwin_final.ui.theme.CheckWin_FinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CheckWin_FinalTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MyApp(
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
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Calcula Derrotas") {
        composable("Calcula Derrotas") {
            CalculaDerrotas(
                navigateToDerrotas = { navController.navigate("Calcula Vitorias") }
            )
        }
        composable("Calcula Vitorias") {
            NewPage(
                navigateToVitorias = { navController.navigate("Calcula Derrotas") }
            )
        }
    }
}

fun CalculaDerrotas(navigateToDerrotas: () -> Unit,modifier: Modifier = Modifier) {

}

@Composable
fun CalculaVitorias(navigateToVitorias: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
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
                text = stringResource(R.string.Bottoes),
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )
            Button(onClick = { navigateToVitorias() }) {
                Text(text = "Mudar para Vitorias")
            }

        }
    }
}

@Composable
fun NewPage(navigateToVitorias: () -> Unit, modifier: Modifier = Modifier) {

    var quantidadeJogosInput by remember {
        mutableStateOf("")
    }

    var percentagemVitoriasageInput by remember {
        mutableStateOf("0")
    }

    var roundUpTip by remember {
        mutableStateOf(false)
    }

    val quantidadeJogos = quantidadeJogosInput.toDoubleOrNull() ?: 0.0
    val percentagemVitorias = percentagemVitoriasageInput.toDoubleOrNull() ?: 0.0

    val derrotas = CalculaDerrotas(quantidadeJogos.toInt(), percentagemVitorias.toInt(), roundUpTip)

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
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

            EditNumberFieldDerrotas(
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

            EditNumberFieldDerrotas(
                labelText = stringResource(R.string.bill_quantidadeVitorias),
                value = percentagemVitoriasageInput,
                onValueChange = { newValue -> percentagemVitoriasageInput = newValue },
                action = ImeAction.Done,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            RoundUpTipRowDerrotas(
                checked = roundUpTip,
                onCheckedChange = { newValue -> roundUpTip = newValue },
                navController = rememberNavController(),
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
