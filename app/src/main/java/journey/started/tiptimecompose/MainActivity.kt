package journey.started.tiptimecompose

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import journey.started.tiptimecompose.ui.theme.TipTimeComposeTheme
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Screen()
                }
            }
        }
    }
}




@Composable
fun Screen(mod : Modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(align = Alignment.TopStart)) {
    val list = listOf ("Excellent (20%)", "Good (18%)", "Okay (15%)", "Bad (10%)")

    var input by remember { mutableStateOf("")}
    var check = remember { mutableStateOf(false) }
    var selectedValue = remember { mutableStateOf("Vaibhav")}

    var costInDouble = input.toDoubleOrNull() ?: 0.0

    var tip = remember {
        mutableStateOf("0.0")
    }

    Column() {
        Text(text = stringResource(id = R.string.app_name), fontSize = 28.sp,
             fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth().padding(top =10.dp)
                .background(color = colorResource(id = R.color.purple_500)),
            textAlign = TextAlign.Center ,color = Color.White
            )
        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = input, shape = CircleShape , modifier = Modifier.fillMaxWidth(),
                onValueChange = { input = it },
                label = { Text(text = "Cost of Service") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "How was the Service?",
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
            )

            RadioButtonComposable( selec =  selectedValue, list = list)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Round up tip?")
                Switch(checked = check.value, onCheckedChange = { check.value = it })
            }

            Button(onClick = {tip.value = calculation(cost = costInDouble, list = list ,
                selectedValue = selectedValue.value,
                check = check.value
            )},
                modifier = Modifier.fillMaxWidth(), enabled = true,
                shape = CircleShape
            ) {
                Text(text = "Calculate", fontSize = 20.sp)
            }

            Text(text = stringResource(id = R.string.tip_amount, tip.value), modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                fontWeight = FontWeight.Bold, fontSize = 23.sp
            )
        }

    }
    }

@Composable
fun RadioButtonComposable(selec: MutableState<String> , list: List<String>) {
    val isSelected: (String) -> Boolean = {selec.value == it}


    Column(horizontalAlignment = Alignment.Start) {
        list.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .selectable(
                    selected = false,
                    onClick = { selec.value = item }
                ))
            {
                RadioButton(selected = isSelected(item), onClick = null)
                Spacer(modifier = Modifier.size(12.dp))
                Text(text = item, fontSize = 20.sp )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}
@VisibleForTesting
internal fun calculation (cost: Double, list: List<String> , selectedValue: String, check: Boolean) : String {
    var amount = 0.0
    when (selectedValue) {
        list[0] -> amount = (20 * cost) / 100
        list[1] -> amount = (18 * cost) / 100
       list[2] ->  amount = (15 * cost) / 100
        list[3] -> amount = (10 * cost) / 100
        else -> amount = 0.0
    }
    if (check) amount = kotlin.math.ceil(amount)

    return NumberFormat.getCurrencyInstance().format(amount)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipTimeComposeTheme {
        Screen()
    }
}