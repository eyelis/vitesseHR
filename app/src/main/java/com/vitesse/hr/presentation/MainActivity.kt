package com.vitesse.hr.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.vitesse.hr.presentation.details.DetailViewModel
import com.vitesse.hr.presentation.edit.EditScreen
import com.vitesse.hr.presentation.edit.EditViewModel
import com.vitesse.hr.presentation.list.ListScreen
import com.vitesse.hr.presentation.list.ListViewModel
import com.vitesse.hr.ui.theme.VitesseHRTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val listViewModel: ListViewModel by viewModels()
    private val editViewModel: EditViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VitesseHRTheme {
                /* Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                     Greeting(
                         name = "Android",
                         modifier = Modifier.padding(innerPadding)
                     )
                 }*/
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        listViewModel = listViewModel,
                        editViewModel = editViewModel,
                        detailViewModel = detailViewModel
                    )
                }

            }
        }
    }
}

@Composable
fun DetailsScreen(any: Any?) {

}

@Composable
fun NavHost(
    navController: NavHostController,
    listViewModel: ListViewModel,
    editViewModel: EditViewModel,
    detailViewModel: DetailViewModel
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
                    navController.navigate(Screen.Edit.route)
                },
                viewModel = listViewModel
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = Screen.Details.navArguments
        ) {
            DetailScreen(
                it.arguments?.getString("id")?.toInt() ?: -1,
                onBackClick = { navController.navigateUp() },
                viewModel = detailViewModel
            )
        }
        composable(route = Screen.Edit.route) {
            EditScreen(
                onBackClick = { navController.navigateUp() },
                onSaveClick = { navController.navigateUp() },
                viewModel = editViewModel
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

/*
@Preview(showBackground = true)
@Composable
private fun CandidateItemPreview() {
    VitesseHRTheme(dynamicColor = false) {
        CandidateItem(
            candidate = Candidate(
                id = 1,
                firstName = "Sileye",
                lastName = "BA",
                phoneNumber = "0658317100",
                email = "basileye@gmail.com",
                isFavorite = true,
                dateOfBirth = "",
                photo = "",
                expectedSalary = "5000",
                note = "joncjnsjnjsnhjnsjnhsjsjsibshsbhbshbshhhhshshshhshshshhshsbhsbhsbhbshbhsbhsbhsb"
            ),
            onCandidateClick = {}
        )
    }
}
*/
