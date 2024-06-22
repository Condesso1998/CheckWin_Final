
package pt.ipg.checkwin_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
                navigateToDerrotas = { navController.navigate("Calcula Derrotas") }
            )
        }
        composable("Calcula Vitorias") {
            NewPage()
        }
    }
}

@Composable
fun MainScreen(
    navigateToDerrotas: () -> Unit,
    navigateToVitorias: () -> Unit,
    modifier: Modifier = Modifier
) {
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = navigateToDerrotas) {
//            Text("Calcular derrotas")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = navigateToVitorias) {
//            Text("Calcular vitórias")
//        }
//    }
//}
}

@Composable
fun CalculaDerrotas(navigateToDerrotas: () -> Unit, modifier: Modifier=Modifier) {
    // Placeholder implementation
    //  Text("Calcula Derrotas Screen")

//
//@Composable
//fun NewPage() {
//    // Placeholder implementation
//    Text("Calcula Vitorias Screen")
//}
//

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








            Button(onClick = { }) {
                // navigate to Vitorias
                // navController.navigate("Vitorias")
                Text(text = "Mudar para Vitorias")
            }
            Button(onClick = { }) {
                // navigate to Vitorias
                // navController.navigate("Vitorias")
                Text(text = "Mudar para Derrotas ")
            }
        }
    }
}
