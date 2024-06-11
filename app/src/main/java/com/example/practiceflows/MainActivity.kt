package com.example.practiceflows

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.practiceflows.ui.theme.PracticeFlowsTheme
import com.example.practiceflows.ui.theme.UseCaseFlowResult
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: MAinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.result.collect { result ->
                when (result) {
                    is UseCaseFlowResult.Failed -> {
                        Log.i( "Aylin","Error: ${result.error.message}")
                    }
                    is UseCaseFlowResult.Loading -> {
                        Log.i( "Aylin","Loading...")
                    }
                    is UseCaseFlowResult.Succeed -> {
                        Log.i( "Aylin","Posts loaded: ${result.data}")
                    }
                }
            }
        }
        viewModel.executeUseCases()

        enableEdgeToEdge()
        setContent {
            PracticeFlowsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
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
    PracticeFlowsTheme {
        Greeting("Android")
    }
}