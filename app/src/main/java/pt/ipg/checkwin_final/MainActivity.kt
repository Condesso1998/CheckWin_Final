

@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package pt.ipg.checkwin_final



import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//import androidx.benchmark.perfetto.Row

import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CheckWin_FinalTheme {

                Scaffold(
                    modifier =
                    Modifier.fillMaxSize()
                ) { innerPadding ->
                    DerrotaLayout_1(
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


@Preview
@Composable
fun DerrotaLayout_1(modifier: Modifier = Modifier) {
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

    val Derrotas =
        CalculaDerrotas(quantidadeJogos.toInt(), percentagemVitorias.toInt(), roundUpTip)


    Box(
        modifier = modifier
            .fillMaxSize()
    )
    {
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

            RoundUpTipRowDerrotas(
                checked = roundUpTip,
                onCheckedChange = { newValue -> roundUpTip = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.quantidadeDerrotas, Derrotas),
                style = MaterialTheme.typography.displaySmall
            )
            Text(text = "Mudar para calculo de Vitorias")

//            Button(onClick = {  }) {
//                navController.navigate("Vitorias")
//                Text(text = "Mudar")
//            }
        }
    }

}

@Composable
fun RoundUpTipRowDerrotas(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.mudaDerrota),
            color = Color.Yellow,
            modifier = Modifier
                .padding(bottom = 32.dp)
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun TipTimePreviewDerrotas() {
    CheckWin_FinalTheme {
        DerrotaLayout_1(
            modifier = Modifier.fillMaxSize(),
            //  navController = rememberNavController()
        )
    }
}

@SuppressLint("SuspiciousIndentation")
private fun CalculaDerrotas(
    quantidadeJogos: Int,
    percentagemDerrotas: Int = 0,
    roundUpTip: Boolean = false
): Int {
    var Derrotas = (percentagemDerrotas / 100.0) * quantidadeJogos


    if (roundUpTip) {
        Derrotas = ceil(Derrotas)
    }

    return Derrotas.toInt()
}

