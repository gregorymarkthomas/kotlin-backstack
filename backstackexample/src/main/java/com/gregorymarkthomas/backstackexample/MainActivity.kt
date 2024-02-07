package com.gregorymarkthomas.backstackexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.widget.ConstraintLayout
import com.gregorymarkthomas.backstack.interfaces.ModelInterface
import com.gregorymarkthomas.backstack.view.BackStackLayout
import com.gregorymarkthomas.backstack.view.BackStackView
import com.gregorymarkthomas.backstack.view.BackstackActivity
import com.gregorymarkthomas.backstackexample.ui.theme.BackstackTheme

class MainActivity : BackstackActivity() {
    override fun getInitialView(): BackStackView {
        TODO("Not yet implemented")
    }

    override fun addView(view: BackStackLayout) {
        TODO("Not yet implemented")
    }

    override fun removeAllViews() {
        TODO("Not yet implemented")
    }

    override fun getModel(): ModelInterface {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackstackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
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
    BackstackTheme {
        Greeting("Android")
    }
}