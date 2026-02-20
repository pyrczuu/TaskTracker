package com.example.todotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todotracker.ui.theme.TodoTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CardsList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    task: TaskItem,
    onCompleteStep: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(32.dp))
    ) {
        Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
            Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 32.dp),
                    textAlign = TextAlign.Center,
                    text = task.name,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (task.isComplete) TextDecoration.LineThrough else null
                )
            }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
//                .background(color = Color.Blue)
                .padding(16.dp)
        ) {
            customProgressIndicator(
                task = task,
                color = Color.Magenta,
            )
        }
    }
}

@Composable
fun CardsList(modifier: Modifier = Modifier){
    var taskText by remember { mutableStateOf("") }
    val taskItems = remember { mutableStateListOf<TaskItem>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var sliderPosition by remember { mutableFloatStateOf(1f) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp)
        ) {
            OutlinedTextField(
                value = taskText,
                onValueChange = { taskText = it },
                placeholder = { Text(text = "Name of your task") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = Color.White)
            )
            IconButton(onClick = {
                if (taskText.isNotBlank()){
                    taskItems.add(
                        TaskItem(
                            name = taskText,
                            stepsTotal = 1)
                    )
                    taskText = ""
                }
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add task button",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp)
                .padding(horizontal = 20.dp)
        ) {
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it},
                steps = 10,
                valueRange = 1f..10f,
                )
        }
        Row() {
            Text(
                text = "Liczba kroków: " + sliderPosition.toInt().toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn{
                    items(taskItems) { taskItem ->
                        TaskCard(taskItem, onCompleteStep = {
                            val index = taskItems.indexOf(taskItem)
                            taskItems[index] = taskItem.copy(stepsCompleted = taskItem.stepsCompleted+1)
                        },
                            onDelete = {
                                taskItems.remove(taskItem)
                            })
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListPreview(){
    TodoTrackerTheme() {
        CardsList()
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    TodoTrackerTheme() {
        TaskCard(
            task = TaskItem(name = "test", stepsCompleted = 1, stepsTotal = 3),
            onCompleteStep = {},
            onDelete = {})
    }
}

