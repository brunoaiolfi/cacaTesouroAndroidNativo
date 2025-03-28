package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.ui.theme.NavigationTheme
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "/home"
            ) {
                composable("/home") {
                    Home(navController)
                }
                composable("/dica1") {
                    Dica(
                        "Segunda letra do alfabeto",
                        onClickProximaDica = { navController.navigate("/dica2") },
                        onClickAcertou = { navController.navigate("/tesouro") }
                    )
                }
                composable("/dica2") {
                    Dica(
                        "Vem depois do A no alfabeto",
                        onClickProximaDica = { navController.navigate("/dica3") },
                        onClickAcertou = { navController.navigate("/tesouro") }
                    )
                }
                composable("/dica3") {
                    Dica(
                        "Vem antes do c no alfabeto",
                        onClickProximaDica = { },
                        possuiProximaDica = false,
                        onClickAcertou = { navController.navigate("/tesouro") }
                    )
                }
                composable("/tesouro") {
                    Tesouro(
                        onClickVoltarHome = { navController.popBackStack("/home", false) }
                    )
                }
            }
        }
    }
}

@Composable
fun Home(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Caça ao tesouros",
        )
        Button(onClick = {
            navController.navigate("/dica1")
        }) { Text("Iniciar") }
    }
}

@Composable
fun Dica(
    dica: String,
    onClickProximaDica: () -> Unit,
    onClickAcertou: () -> Unit,
    possuiProximaDica: Boolean = true
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dica,
        )

        ButtonsRespostas(onClickAcertou)

        if (possuiProximaDica) {
            Button(onClick = onClickProximaDica) {
                Text(text = "Próxima dica")
            }
        }
    }
}

@Composable
fun ButtonsRespostas(
    onClickAcertou: () -> Unit
) {
    var textResult = remember { mutableStateOf(" "); }

    Column {
        Text(text = textResult.value)
        Button(onClick = { textResult.value = "Errou :(" }) { Text(text = "A") }
        Button(onClick = onClickAcertou) { Text(text = "B") }
        Button(onClick = { textResult.value = "Errou :(" }) { Text(text = "C") }
    }
}

@Composable
fun Tesouro(onClickVoltarHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Parabéns você achou o tesouro!!!!",
        )
        Button(
            onClick = onClickVoltarHome
        ) { Text("Voltar para a home.") }
    }
}