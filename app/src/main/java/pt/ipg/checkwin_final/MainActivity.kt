
package pt.ipg.checkwin_final


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
          val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Calcula Derrotas", builder = {
                composable("Calcula Derrotas"){
                    CalculaDerrotas(navController)
                }
                composable("Calcula Vitorias"){
                    NewPage()
                }
            }  )
                }

            }








//            CheckWin_FinalTheme {
//                Scaffold(
//                    modifier =
//                    Modifier.fillMaxSize()
//                ) { innerPadding ->
//                    DerrotaLayoutDerrotas(
//                        modifier = Modifier
//                            .padding(innerPadding)
//                            .fillMaxSize()
//                    )
//
//                }
//            }
//        }



    }