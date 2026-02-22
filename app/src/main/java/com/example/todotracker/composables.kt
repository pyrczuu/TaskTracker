package com.example.todotracker
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SweepGradientShader
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todotracker.ui.theme.TodoTrackerTheme

@Composable
fun CustomProgressIndicator(
    task: TaskItem,
    color: Color,
    width: Dp = 400.dp,
    height: Dp = 32.dp,
//    onProgress: () -> Unit
    ){
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(width = width, height = height)
            .background(color = Color.White)
//            .clip(RoundedCornerShape(32.dp))
            .drawWithContent(onDraw = {
                drawRoundRect(
                    brush = Brush.horizontalGradient(colors = listOf(Color.Green, Color.Yellow)),
//                    color = color,
                    size = Size(width = (task.stepsCompleted.toFloat()/task.stepsTotal) * size.width, height = size.height)
                )
            })
    ) {
//        Button(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color.Red)
//                .alpha(1f),
//            onClick = {
//                onProgress
//                Log.i("BAR", "Kliknięto progress bar " + task.stepsCompleted.toString())
//            }
//        ) { }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomProgressPreview(){
    TodoTrackerTheme() {
        CustomProgressIndicator(
            task = TaskItem(name = "test", stepsCompleted = 3, stepsTotal = 3),
            color = Color.Red,
        )
    }
}

@Composable
fun TaskCard(
    task: TaskItem,
    onDelete: () -> Unit,
    onBarClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(32.dp))
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center,
                text = task.name + task.stepsTotal.toString(),
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                textDecoration = if (task.isComplete) TextDecoration.LineThrough else null
            )
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(color = Color.White)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete task")
            }
        }
        Box(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(32.dp))
                .size(width = 400.dp, height = 32.dp)
        ) {
            CustomProgressIndicator(
                task = task,
                color = MaterialTheme.colorScheme.primary,
            )
            Button(
                modifier = Modifier
                    .fillMaxSize()
//                    .background(color = Color.Red)
                    .alpha(0f),
                onClick = {
                    onBarClick()
                    Log.i("BAR", "Kliknięto progress bar " + task.stepsCompleted.toString())
                }
            ) { }
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
            .background(color = MaterialTheme.colorScheme.background)
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
                    .background(color = MaterialTheme.colorScheme.onPrimary)
            )
            IconButton(onClick = {
                if (taskText.isNotBlank()){
                    taskItems.add(
                        TaskItem(
                            name = taskText,
                            stepsTotal = sliderPosition.toInt())
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
                        TaskCard(taskItem, onBarClick = {
                            val index = taskItems.indexOf(taskItem)
                            val isCompleted = taskItem.stepsCompleted >= taskItem.stepsTotal
                            taskItems[index] = taskItem.copy(stepsCompleted = taskItem.stepsCompleted+1, isComplete = isCompleted)
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

//@Preview(showBackground = true)
//@Composable
//fun TaskListPreview(){
//    TodoTrackerTheme() {
//        CardsList()
//    }
//}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    TodoTrackerTheme() {
        TaskCard(
            task = TaskItem(name = "test", stepsCompleted = 2, stepsTotal = 3),
            onBarClick = {},
            onDelete = {})
    }
}

