package com.example.todotracker
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todotracker.ui.theme.TodoTrackerTheme

@Composable
fun customProgressIndicator(
    task: TaskItem,
    color: Color
){
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(width = 300.dp, height = 32.dp)
            .background(color = Color.White)
            .clip(RoundedCornerShape(32.dp))
            .drawBehind(onDraw = {
                drawRoundRect(
                    color = color,
                    size = Size(width = (task.stepsCompleted.toFloat()/task.stepsTotal) * size.width, height = size.height)
                )
            })
    ) {
        Button(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0f),
            onClick = {

                Log.i("BAR", "Kliknięto progress bar")
            }
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
fun customProgressPreview(){
    TodoTrackerTheme() {
        customProgressIndicator(
            task = TaskItem(name = "test", stepsCompleted = 3, stepsTotal = 3),
            color = Color.Red
        )
    }
}