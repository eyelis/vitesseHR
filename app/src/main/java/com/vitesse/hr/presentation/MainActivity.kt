package com.vitesse.hr.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vitesse.hr.presentation.details.DetailScreen
import com.vitesse.hr.presentation.edit.EditScreen
import com.vitesse.hr.presentation.list.ListScreen
import com.vitesse.hr.ui.theme.VitesseHRTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VitesseHRTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController
                    )
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            ListScreen(
                onCandidateClick = {
                    navController.navigate(
                        Screen.Details.createRoute(
                            id = it.id.toString()
                        )
                    )
                },
                onAddClick = {
                    navController.navigate(
                        Screen.Edit.createRoute(
                            id = "-1"
                        )
                    )
                }
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = Screen.Details.navArguments
        ) {
            DetailScreen(
                id = it.arguments?.getString("id")?.toInt() ?: -1,
                onBackClick = { navController.navigateUp() },
                onEditClick = { id ->
                    navController.navigate(
                        Screen.Edit.createRoute(
                            id = id.toString()
                        )
                    )
                }
            )
        }
        composable(
            route = Screen.Edit.route,
            arguments = Screen.Edit.navArguments
        ) {
            EditScreen(
                id = it.arguments?.getString("id")?.toInt() ?: -1,
                onBackClick = { navController.navigateUp() },
                onSaveClick = { navController.navigate(Screen.Home.route) }
            )
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VitesseHRTheme {
        Greeting("Android")
    }
}
