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
import androidx.compose.runtime.*
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
    NavHost(navController = navController, startDestination = "Inicio") {
        composable("Inicio") {
            Inicio(
                navigateToDerrotas = { navController.navigate("Calcula Derrotas") },
                navigateToVitorias = { navController.navigate("Calcula Vitorias") }
            )
        }
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

@Composable
fun Inicio(navigateToVitorias: () -> Unit, navigateToDerrotas: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
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
                text = "Escolha uma opção",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )
            Button(onClick = navigateToDerrotas) {
                Text(text = "Mudar para Derrotas")
            }
            Button(onClick = navigateToVitorias) {
                Text(text = "Mudar para Vitorias")
            }
        }
    }
}


@Composable
fun CalculaDerrotas(navigateToDerrotas: () -> Unit, modifier: Modifier = Modifier) {
    var quantidadeJogosInput by remember { mutableStateOf("") }
    var percentagemDerrotasInput by remember { mutableStateOf("0") }
    var roundUpTip by remember { mutableStateOf(false) }

    val quantidadeJogos = quantidadeJogosInput.toDoubleOrNull() ?: 0.0
    val quantidadeDerrotas = percentagemDerrotasInput.toDoubleOrNull() ?: 0.0



    val percentagemDerrotas = if (quantidadeJogos > 0) {
        quantidadeDerrotas / quantidadeJogos * 100
    } else {
        0.0
    }


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
                text = "Insira os Jogos",
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            EditNumberFieldDerrotas(
                labelText = "Quantidade de Jogos",
                value = quantidadeJogosInput,
                onValueChange = { newValue -> quantidadeJogosInput = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Insira a Percentagem de Derrotas",
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            EditNumberFieldDerrotas(
                labelText = "Quantidade de Derrotas",
                value = percentagemDerrotasInput,
                onValueChange = { newValue -> percentagemDerrotasInput = newValue },
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
                text = "Percentagem de Derrotas: %.2f%%".format(percentagemDerrotas),
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

        }
    }
}

@Composable
fun NewPage(navigateToVitorias: () -> Unit, modifier: Modifier = Modifier) {
    // State variables for user input
    var quantidadeJogosInput by remember { mutableStateOf("") }
    var quantidadeVitoriasInput by remember { mutableStateOf("") }
    var roundUpTip by remember { mutableStateOf(false) }

    // Convert inputs to Double or use 0.0 as default
    val quantidadeJogos = quantidadeJogosInput.toDoubleOrNull() ?: 0.0
    val quantidadeVitorias = quantidadeVitoriasInput.toDoubleOrNull() ?: 0.0

    // esta assim porque se declar a varivel primeiro e depois fazier a conta no if da crash

    val percentagemVitorias = if (quantidadeJogos > 0) {
        quantidadeVitorias / quantidadeJogos * 100
    } else {
        0.0
    }

    Box(modifier = modifier.fillMaxSize()) {
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
                text = "Insira os Jogos e Vitórias",
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            EditNumberFieldDerrotas(
                labelText = "Quantidade de Jogos",
                value = quantidadeJogosInput,
                onValueChange = { newValue -> quantidadeJogosInput = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            EditNumberFieldDerrotas(
                labelText = "Quantidade de Vitórias",
                value = quantidadeVitoriasInput,
                onValueChange = { newValue -> quantidadeVitoriasInput = newValue },
                action = ImeAction.Done,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Percentagem de Vitórias: %.2f%%".format(percentagemVitorias),
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.Start)
            )

            if (percentagemVitorias > 80) {
                Image(
                    painter = painterResource(id = R.drawable.img_1),
                    contentDescription = "Success",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(128.dp)
                )
                Text(text = "Parabéns, pelo sucesso")
            }
            if (percentagemVitorias < 30) {
                Image(
                    painter = painterResource(id = R.drawable.img_2),
                    contentDescription = "Success",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(128.dp)
                )
                Text(text = "Tens de Treinar mais")
            }
        }
    }
}


