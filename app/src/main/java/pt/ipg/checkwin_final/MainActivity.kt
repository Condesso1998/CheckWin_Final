
package pt.ipg.checkwin_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = navigateToDerrotas) {
            Text("Calcular derrotas")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = navigateToVitorias) {
            Text("Calcular vitórias")
        }
    }
}

@Composable
fun CalculaDerrotas(navigateToDerrotas: () -> Unit) {
    // Placeholder implementation
    Text("Calcula Derrotas Screen")
}
//
//@Composable
//fun NewPage() {
//    // Placeholder implementation
//    Text("Calcula Vitorias Screen")
//}
